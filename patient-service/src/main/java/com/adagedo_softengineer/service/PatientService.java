package com.adagedo_softengineer.service;

import org.springframework.stereotype.Service;

import com.adagedo_softengineer.model.Patient;
import com.adagedo_softengineer.patient.Empty;
import com.adagedo_softengineer.patient.GetPatientDetailRequest;
import com.adagedo_softengineer.patient.GetPatientDetailsResponse;
import com.adagedo_softengineer.patient.PatientRegistrationRequest;
import com.adagedo_softengineer.patient.PatientRegistrationResponse;
import com.adagedo_softengineer.patient.PatientServiceGrpc.PatientServiceImplBase;
import com.adagedo_softengineer.repo.PatientRepository;

import io.grpc.stub.StreamObserver;

@Service
public class PatientService extends PatientServiceImplBase {

    // repository injection 
    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }


    // register patient 
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

                repository.save(patient);
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
                    .setFirstName(p.getFirst_name())
                    .setLastName(p.getLast_name())
                    .setPatientId(p.getId())
                    .setPhone(p.getPhone())
                    .setAddress(p.getAddress())
                    .build());
        } else {
            responseObserver
                    .onError(io.grpc.Status.NOT_FOUND.withDescription("patient not found").asRuntimeException());
        }
        responseObserver.onCompleted();

    }


    // sending stream of data to the erver
    // client side streaming
    @Override
    public StreamObserver<PatientRegistrationRequest> streamPatient(StreamObserver<Empty> responseObserver) {
        
        // return new server stream

        return new StreamObserver<PatientRegistrationRequest>(){

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
                
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
                
            }

            @Override
            public void onNext(PatientRegistrationRequest request) {
                Patient patient = new Patient(
                    null, 
                    request.getFirstName(),
                    request.getLastName(), 
                    request.getEmail(),
                    request.getPhone(), 
                    request.getAddress()
                );
                repository.save(patient);
                
            }

        };
    }
    
}