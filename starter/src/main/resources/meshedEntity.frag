#version 330 core

layout (location = 0) out vec4 FragColor;

in vec2 PassTex;

uniform sampler2D sampler;

void main() {
    gl_FragColor = vec4(texture(sampler, PassTex).rgb, 1.0);
}