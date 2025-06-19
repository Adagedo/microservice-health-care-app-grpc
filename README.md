# microservice with gRPC api.

## what Excalty is gRPC???

gRPC is a cutting edge open source high performance state of the art remote procedure call (rpc) frame work. 
In a simple form, think of gRPC as a fast flight service for data transfer.

## Use Cases??

gRPC enables communication between wide range of device ranging from mobile, decktop, web, and backend intrastructure. This versatility makes it perfect for building microservice applications.

## Why gRPC??

- gRPC support muiltiple languages and platforms.
- High performance.
- pluggable transport, and serialization interceptors.
- Features rich and it suitable for communications between microservices. 

## Key Design Descisions

gRPC leverages protocol buffers for code generation, data serializations and interface generation.
All gRPC language implementations leverages the protobuff plugin to generate interface, therefore this languages can interact with each others.
Protocol buffers uses binary encodings and its reduces data size during data transfer and serializations.