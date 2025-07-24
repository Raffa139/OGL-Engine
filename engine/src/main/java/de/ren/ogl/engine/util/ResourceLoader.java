package de.ren.ogl.engine.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.FileSystem;
import java.util.HashMap;

@Component
public final class ResourceLoader {
  public Resource locateResource(String file, Class<?> clazz) throws FileNotFoundException {
    URL url = clazz.getClassLoader().getResource(file);

    if (url == null) {
      throw new FileNotFoundException(file);
    }

    try {
      URI uri = url.toURI();

      if (uri.getScheme().equals("jar")) {
        return fromJar(uri);
      }

      return new Resource(null, uri);
    } catch (URISyntaxException e) {
      throw new FileNotFoundException(file);
    }
  }

  private Resource fromJar(URI uri) {
    String[] uriParts = uri.toString().split("!");
    URI jarUri = URI.create(uriParts[0]);
    URI resourceUri = URI.create(uriParts[1]);

    FileSystem fs;
    try {
      fs = FileSystems.newFileSystem(jarUri, new HashMap<>());
    } catch (FileSystemAlreadyExistsException e) {
      fs = FileSystems.getFileSystem(jarUri);
    } catch (IOException e) {
      throw new UncheckedIOException(String.format("Failed to create of find file system for JAR %s", jarUri), e);
    }

    return new Resource(fs, resourceUri);
  }

  public static class Resource {
    private final FileSystem fs;

    private final URI uri;

    private Resource(FileSystem fs, URI uri) {
      this.fs = fs;
      this.uri = uri;
    }

    public Path toPath() {
      if (fs != null) {
        return fs.getPath(uri.toString());
      }

      return Paths.get(uri);
    }

    public File toFile() {
      return new File(toString());
    }

    public FileInputStream toFileInputStream() throws FileNotFoundException {
      return new FileInputStream(toFile());
    }

    public FileReader toFileReader() throws FileNotFoundException {
      return new FileReader(toFile());
    }

    @Override
    public String toString() {
      return toPath().toString();
    }
  }
}
