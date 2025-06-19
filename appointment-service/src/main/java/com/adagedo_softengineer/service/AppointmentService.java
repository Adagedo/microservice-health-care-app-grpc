package com.adagedo_softengineer.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.adagedo_softengineer.appointment.AppointmentAvailabilityRequest;
import com.adagedo_softengineer.appointment.AppointmentAvailabilityResponse;
import com.adagedo_softengineer.appointment.AppointmentServiceGrpc.AppointmentServiceImplBase;
import com.adagedo_softengineer.appointment.AppointmentSlot;
import com.adagedo_softengineer.appointment.BookAppointmentRequest;
import com.adagedo_softengineer.appointment.BookAppointmentResponse;
import com.adagedo_softengineer.doctor.DoctorServiceGrpc;
import com.adagedo_softengineer.doctor.GetDoctorDetailsRequest;
import com.adagedo_softengineer.model.Appointment;
import com.adagedo_softengineer.patient.GetPatientDetailRequest;
import com.adagedo_softengineer.patient.PatientServiceGrpc;
import com.adagedo_softengineer.repo.AppointmentRepository;

import io.grpc.stub.StreamObserver;

@Service
public class AppointmentService extends AppointmentServiceImplBase {
    

    private final AppointmentRepository repository;

    private DoctorServiceGrpc.DoctorServiceBlockingStub doctorServiceBlockingStub;

    private PatientServiceGrpc.PatientServiceBlockingStub patientServiceBlockingStub;

    public AppointmentService(AppointmentRepository repository, DoctorServiceGrpc.DoctorServiceBlockingStub doctorServiceBlockingStub, PatientServiceGrpc.PatientServiceBlockingStub patientServiceBlockingStub) {
        this.repository = repository;
        this.doctorServiceBlockingStub = doctorServiceBlockingStub;
        this.patientServiceBlockingStub = patientServiceBlockingStub;
    }

    // book appointment
    @Override
    public void bookAppointment(BookAppointmentRequest request,
            StreamObserver<BookAppointmentResponse> responseObserver) {

        try {

            var doctorResponse = doctorServiceBlockingStub
                .getDoctorDetails(GetDoctorDetailsRequest.newBuilder().setDoctorId(request.getDoctorId()).build());
    
            var patientRespone = patientServiceBlockingStub
                    .getPatientDetails(GetPatientDetailRequest.newBuilder().setPatientId(request.getPatientId()).build());
            
            Appointment appointment = new Appointment(
                null,
                doctorResponse.getDoctorId(), 
                patientRespone.getPatientId(), 
                patientRespone.getFirstName() + " " + patientRespone.getLastName(), 
                doctorResponse.getFirstName() + " " + doctorResponse.getLastName(), 
                LocalDate.parse(request.getAppointmentDate()),
                LocalTime.parse(request.getAppointmentTime()), 
                request.getPurpose()
            );

            var appointment_response = repository.save(appointment);
            responseObserver.onNext(BookAppointmentResponse.newBuilder().setAppointmentId(appointment_response.getId()).build());
        } catch (io.grpc.StatusRuntimeException e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        }

        responseObserver.onCompleted();
        
    }

    @Override
    public void getAppointmentAvailability(AppointmentAvailabilityRequest request,
            StreamObserver<AppointmentAvailabilityResponse> responseObserver) {
 
        List<LocalDateTime> someDummyHardcodedAppointments =  Arrays.asList(
            LocalDateTime.of(2025, 1, 7, 9,0),
            LocalDateTime.of(2025, 1, 8, 9,10),
            LocalDateTime.of(2025, 1, 9, 11,0),
            LocalDateTime.of(2025, 1, 11, 10,20),
            LocalDateTime.of(2025, 1, 13, 8,50),
            LocalDateTime.of(2025, 1, 14, 5,31),
            LocalDateTime.of(2025, 1, 16, 17,50),
            LocalDateTime.of(2025, 1, 18, 12,30)
        );

        Random random = new Random();
        int i=0;

        while(i<10){
            Collections.shuffle(someDummyHardcodedAppointments);

            List<AppointmentSlot> slots = someDummyHardcodedAppointments.stream().limit(2)
                .map(dateTime -> AppointmentSlot.newBuilder()
                    .setAppointmentDate(dateTime.toLocalDate().toString())
                    .setAppointmentTime(dateTime.toLocalTime().toString())
                    .build()
                )
                .collect(Collectors.toList());

                AppointmentAvailabilityResponse response = AppointmentAvailabilityResponse.newBuilder()
                    .addAllResponse(slots)
                    .setAvailableAsOf(LocalDateTime.now().toString()).build();

                responseObserver.onNext(response);

                try {
                    
                    Thread.sleep(2000);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);

                }

                responseObserver.onCompleted();
                i++;
        }
    }

}
