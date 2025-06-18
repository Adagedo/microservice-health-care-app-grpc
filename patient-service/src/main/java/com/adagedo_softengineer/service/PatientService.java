package com.adagedo_softengineer.service;

import org.springframework.stereotype.Service;

import com.adagedo_softengineer.model.Patient;
import com.adagedo_softengineer.patient.GetPatientDetailRequest;
import com.adagedo_softengineer.patient.GetPatientDetailsResponse;
import com.adagedo_softengineer.patient.PatientRegistrationRequest;
import com.adagedo_softengineer.patient.PatientRegistrationResponse;
import com.adagedo_softengineer.patient.PatientServiceGrpc.PatientServiceImplBase;
import com.adagedo_softengineer.repo.PatientRepository;

import io.grpc.stub.StreamObserver;

@Service
public class PatientService extends PatientServiceImplBase{

    // repository injections 
    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

        // register a new patient 
     @Override
     public void registerPatient(PatientRegistrationRequest request,
             StreamObserver<PatientRegistrationResponse> responseObserver) {
        
                Patient patient = new Patient(
                    null, 
                        request.getFirstName(), 
                        request.getLastName(), 
                        request.getEmail(), 
                        request.getPhone(), 
                        request.getAddress()
                );
                // save patient to database 
                repository.save(patient);

                // return a response to the grpc server 

                responseObserver.onNext(PatientRegistrationResponse.newBuilder().setPatientId(patient.getId()).build());
                responseObserver.onCompleted();
        }
    @Override
    public void getPatientDetails(GetPatientDetailRequest request,
            StreamObserver<GetPatientDetailsResponse> responseObserver) {
        var patient = repository.findById(request.getPatientId());
        if (patient.isPresent()) {
            var p = patient.get();
            responseObserver.onNext(GetPatientDetailsResponse.newBuilder()
                .setPatientId(p.getId())
                .setFirstName(p.getFirst_name())
                .setLastName(p.getLast_name())
                .setEmail(p.getEmail())
                .setPhone(p.getPhone())
                .setAddress(p.getAddress())
                .build()
            );
        } else {
            responseObserver
                    .onError(io.grpc.Status.NOT_FOUND.withDescription("patient not found").asRuntimeException());
        }
        
        responseObserver.onCompleted();
    }



    
}