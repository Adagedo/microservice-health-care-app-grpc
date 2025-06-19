package com.adagedo_softengineer.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.adagedo_softengineer.doctor.DoctorRegistrationRequest;
import com.adagedo_softengineer.doctor.DoctorRegistrationResponse;
import com.adagedo_softengineer.doctor.DoctorServiceGrpc.DoctorServiceImplBase;
import com.adagedo_softengineer.doctor.GetDoctorDetailsRequest;
import com.adagedo_softengineer.doctor.GetDoctorDetailsResponse;
import com.adagedo_softengineer.doctor.MessageRequest;
import com.adagedo_softengineer.model.Doctor;
import com.adagedo_softengineer.repo.DoctorRepository;

import io.grpc.stub.StreamObserver;

@Service
public class DoctorService extends DoctorServiceImplBase {

    private final DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    // register doctor 
    @Override
    public void regiterDoctor(DoctorRegistrationRequest request,
            StreamObserver<DoctorRegistrationResponse> responseObserver) {

        Doctor doctor = new Doctor(
            null, 
            request.getFirstName(), 
            request.getLastName(), 
            request.getEmail(), 
            request.getPhone(), 
            request.getAddress(), 
            request.getSpecialty(), 
            request.getCenterName(), 
            request.getLocation()
        );

        var d = repository.save(doctor);
        responseObserver.onNext(DoctorRegistrationResponse.newBuilder().setDoctorId(d.getId()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getDoctorDetails(GetDoctorDetailsRequest request,
            StreamObserver<GetDoctorDetailsResponse> responseObserver) {

        Optional<Doctor> doctor = repository.findById(request.getDoctorId());
        
        if (doctor.isPresent()) {
            var d = doctor.get();
            responseObserver.onNext(GetDoctorDetailsResponse.newBuilder()
                .setDoctorId(d.getId())
                .setAddress(d.getAddress())
                .setFirstName(d.getFirst_name())
                .setLastName(d.getLast_name())
                .setPhone(d.getPhone())
                .setSpecialty(d.getSpecialty())
                .setCenterName(d.getCenter_name())
                .setLocation(d.getLocation())
                .build()
            );
        } else {
            responseObserver.onError(io.grpc.Status.NOT_FOUND.withDescription("doctor not found").asRuntimeException());
        }
        responseObserver.onCompleted();
            

    }

    // client streamig and server streaming implementations 
    @Override
    public StreamObserver<MessageRequest> chat(StreamObserver<MessageRequest> responseObserver) {

        return new StreamObserver<MessageRequest>(){

            @Override 
            public void onNext(MessageRequest chatMessageRequest){

                String message = "message";
                MessageRequest response = MessageRequest.newBuilder()
                    .setMessage(message)
                    .setFrom("Doctor")
                    .setTo("Patient")
                    .setTimestamp(LocalDateTime.now().toString())
                    .build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t){
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                String message = "deliverd";
                MessageRequest response = MessageRequest.newBuilder()
                    .setMessage(message)
                    .setFrom("Doctor")
                    .setTo("Patient")
                    .setTimestamp(LocalDateTime.now().toString())
                    .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }

        };
    }


    // register doctor 
    

}
