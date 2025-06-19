package com.adagedo_softengineer.service;

import org.springframework.stereotype.Service;

import com.adagedo_softengineer.appointment.AppointmentAvailabilityRequest;
import com.adagedo_softengineer.appointment.AppointmentAvailabilityResponse;
import com.adagedo_softengineer.appointment.AppointmentServiceGrpc.AppointmentServiceImplBase;
import com.adagedo_softengineer.appointment.BookAppointmentRequest;
import com.adagedo_softengineer.appointment.BookAppointmentResponse;
import com.adagedo_softengineer.repo.AppointmentRepository;

import io.grpc.stub.StreamObserver;

@Service
public class AppointmentService extends AppointmentServiceImplBase {
    
    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    // book appointment
    @Override
    public void bookAppointment(BookAppointmentRequest request,
            StreamObserver<BookAppointmentResponse> responseObserver) {

    }

    @Override
    public void getAppointmentAvailability(AppointmentAvailabilityRequest request,
            StreamObserver<AppointmentAvailabilityResponse> responseObserver) {
 
    }

    

    

}
