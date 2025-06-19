package com.adagedo_softengineer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

import com.adagedo_softengineer.doctor.DoctorServiceGrpc;
import com.adagedo_softengineer.patient.PatientServiceGrpc;

@Configuration
public class ConfigStub {
    
    @Bean
    DoctorServiceGrpc.DoctorServiceBlockingStub doctorServiceBlockingStub(GrpcChannelFactory channelFactory) {
        return DoctorServiceGrpc.newBlockingStub(channelFactory.createChannel("doctorService"));
    }

    @Bean
    PatientServiceGrpc.PatientServiceBlockingStub patientServiceBlockingStub(GrpcChannelFactory channelFactory) {
        return PatientServiceGrpc.newBlockingStub(channelFactory.createChannel("patientService"));
    }
}
