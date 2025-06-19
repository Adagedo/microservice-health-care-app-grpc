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

## RPC calls

### Patient Services calls

#### List Service 
```bash
grpcurl -plaintext localhost:9090 list
```

#### gRPC UI 
```bash
grpcui -plaintext localhost:9090
```

### Register a patient

to register a patient run:

```bash
grpcurl -plaintext -d'{
    "first_name":"Adagedo", 
    "last_name" : "Israel", 
    "email" : "ada@gmail.com",
    "phone": "+234-8160-995-531", 
    "address": "123 St peter"
}' localhost:9090 com.adagedo_softengineer.patient.Patient/RegisterPatient
```

### Get a petient
to a patients info

```bash
grpcurl -plaintext -d'{
    "id":1
}' localhost:9090 com.adagedo_softengineer.patient.Patient/GetPatientDetails
```

### Doctor Service Calls

### Register Doctor call
To register a doctor:

```bash
grpcurl -plaintext -d'{
    "first_name":"James", 
    "last_name" : "Smith", 
    "email" : "smaith@gmail.com",
    "phone": "+234-2344-995-531", 
    "address": "123 St peter"
}' localhost:9090 com.adagedo_softengineer.patient.Patient/RegisterDoctor
```
### GetDoctorDetail

To get a doctors info:
```bash
grpcurl -plaintext -d'{
    "id":1
}' localhost:9090 com.adagedo_softengineer.patient.Patient/GetDoctorDetails
```

