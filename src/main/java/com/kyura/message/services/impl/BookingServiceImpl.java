package com.kyura.message.services.impl;

import com.kyura.message.models.Booking;
import com.kyura.message.payload.response.BookingReponse;
import com.kyura.message.repository.BookingRepository;
import com.kyura.message.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<BookingReponse> findAll() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingReponse> bookingReponseList = new ArrayList<>();
        for(Booking item: bookings){
            BookingReponse bookingReponse = new BookingReponse();
            bookingReponse.setId(item.getId());
            bookingReponse.setCode(item.getCode());
            bookingReponse.setFullNameUser(item.getUser().getFullname());
            bookingReponse.setPhoneUser(item.getUser().getPhone());
            bookingReponse.setNameHospital(item.getDoctors().getHospitals().getName());
            bookingReponse.setAddressHospital(item.getDoctors().getHospitals().getAddress());
            bookingReponse.setNamePackageClinic(item.getPackageClinic().getName());
            bookingReponse.setPricePackageClinic(item.getPackageClinic().getPrice());
            bookingReponse.setFullNameDoctor(item.getDoctors().getUser().getFullname());
            bookingReponse.setBooking_date(item.getBooking_date());
            bookingReponse.setBooking_time(item.getBooking_time());
            bookingReponse.setDescription(item.getDescription());
            bookingReponse.setStatus(item.getStatus());
           // bookingReponse.setStatusTransaction(item.getTransaction().getStatus());

            bookingReponseList.add(bookingReponse);
        }

        return bookingReponseList;
    }

    @Override
    public List<BookingReponse> findById(Long id) {
        Optional<Booking> bookings =  bookingRepository.findById(id);
        List<BookingReponse> bookingReponseList = new ArrayList<>();
        if (bookings.isPresent()) {
            Booking item = bookings.get();
            BookingReponse bookingReponse = new BookingReponse();
            bookingReponse.setId(item.getId());
            bookingReponse.setCode(item.getCode());
            bookingReponse.setFullNameUser(item.getUser().getFullname());
            bookingReponse.setPhoneUser(item.getUser().getPhone());
            bookingReponse.setNameHospital(item.getDoctors().getHospitals().getName());
            bookingReponse.setAddressHospital(item.getDoctors().getHospitals().getAddress());
            bookingReponse.setNamePackageClinic(item.getPackageClinic().getName());
            bookingReponse.setPricePackageClinic(item.getPackageClinic().getPrice());
            bookingReponse.setFullNameDoctor(item.getDoctors().getUser().getFullname());
            bookingReponse.setBooking_date(item.getBooking_date());
            bookingReponse.setBooking_time(item.getBooking_time());
            bookingReponse.setDescription(item.getDescription());
            bookingReponse.setStatus(item.getStatus());
            bookingReponseList.add(bookingReponse);
        }
        return bookingReponseList;
    }
}
