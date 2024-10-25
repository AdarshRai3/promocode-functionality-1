package com.bootcodingTask.promocode_functionality.promocode_functionality.services;

import com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.basic.CoursesBasicDto;
import com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.reponse.PromocodesResponse;
import com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.request.PromocodesRequests;
import com.bootcodingTask.promocode_functionality.promocode_functionality.entity.Courses;
import com.bootcodingTask.promocode_functionality.promocode_functionality.entity.Promocodes;
import com.bootcodingTask.promocode_functionality.promocode_functionality.exceptions.DuplicatePromoCodeException;
import com.bootcodingTask.promocode_functionality.promocode_functionality.exceptions.InvalidPromoCodeException;
import com.bootcodingTask.promocode_functionality.promocode_functionality.exceptions.ResourceNotFoundException;
import com.bootcodingTask.promocode_functionality.promocode_functionality.repository.CoursesRepository;
import com.bootcodingTask.promocode_functionality.promocode_functionality.repository.PromocodesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PromocodesServices {

    @Autowired
    private PromocodesRepository promocodesRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    // Create
    public PromocodesResponse createPromocode(PromocodesRequests promocodeRequests) {
        // Check for duplicate promocode
        if (promocodesRepository.findByCode(promocodeRequests.getCode()).isPresent()) {
            throw new DuplicatePromoCodeException("PromoCode " + promocodeRequests.getCode() + " already exists");
        }

        Promocodes promocodes = new Promocodes();
        return saveOrUpdatePromocode(promocodes, promocodeRequests);
    }

    // Get All Promocodes
    public List<PromocodesResponse> getAllPromocodes() {
        List<Promocodes> promocodesList = promocodesRepository.findAll();
        return promocodesList.stream()
                .map(this::mapToPromocodesResponse)
                .collect(Collectors.toList());
    }

    // Get Promocode by ID
    public PromocodesResponse getPromocodeById(Long promocodeId) {
        Promocodes promocode = promocodesRepository.findById(promocodeId)
                .orElseThrow(() -> new ResourceNotFoundException("Promocode not found with id: " + promocodeId));
        return mapToPromocodesResponse(promocode);
    }

    // Update Promocode by ID
    public PromocodesResponse updatePromocode(Long promocodeId, PromocodesRequests promocodeRequests) {
        Promocodes existingPromocode = promocodesRepository.findById(promocodeId)
                .orElseThrow(() -> new ResourceNotFoundException("Promocode not found with id: " + promocodeId));

        // Check for duplicate code
        promocodesRepository.findByCode(promocodeRequests.getCode())
                .ifPresent(promocode -> {
                    if (!promocode.getPromocodeId().equals(promocodeId)) {
                        throw new DuplicatePromoCodeException("PromoCode " + promocodeRequests.getCode() + " already exists");
                    }
                });

        return saveOrUpdatePromocode(existingPromocode, promocodeRequests);
    }

    // Delete Promocode by ID
    public void deletePromocode(Long promocodeId) {
        if (!promocodesRepository.existsById(promocodeId)) {
            throw new ResourceNotFoundException("Promocode not found with id: " + promocodeId);
        }
        promocodesRepository.deleteById(promocodeId);
    }

    // Validate promocode
    public void validatePromocode(String code) {
        Promocodes promocode = promocodesRepository.findByCode(code)
                .orElseThrow(() -> new InvalidPromoCodeException("Invalid promocode: " + code));

        if (!promocode.getIsActive()) {
            throw new InvalidPromoCodeException("Promocode is not active: " + code);
        }

        if (promocode.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidPromoCodeException("Promocode has expired: " + code);
        }
    }

    private PromocodesResponse saveOrUpdatePromocode(Promocodes promocodes, PromocodesRequests promocodeRequests) {
        promocodes.setCode(promocodeRequests.getCode());
        promocodes.setDiscountPercent(promocodeRequests.getDiscountPercent());
        promocodes.setIsActive(promocodeRequests.getIsActive());
        promocodes.setExpiryDate(promocodeRequests.getExpiryDate());

        // Validate each course ID
        List<Long> courseIds = promocodeRequests.getCourseId();
        Set<Long> existingCourseIds = coursesRepository.findAllById(courseIds)
                .stream()
                .map(Courses::getCourseId)
                .collect(Collectors.toSet());

        List<Long> missingCourseIds = courseIds.stream()
                .filter(id -> !existingCourseIds.contains(id))
                .collect(Collectors.toList());

        if (!missingCourseIds.isEmpty()) {
            throw new ResourceNotFoundException("Course IDs not found: " + missingCourseIds);
        }

        List<Courses> coursesList = existingCourseIds.stream()
                .map(coursesRepository::getById)
                .collect(Collectors.toList());

        for (Courses course : coursesList) {
            course.setPromocodes(promocodes);
        }

        promocodesRepository.save(promocodes);
        coursesRepository.saveAll(coursesList);

        return mapToPromocodesResponse(promocodes);
    }

    private PromocodesResponse mapToPromocodesResponse(Promocodes promocodes) {
        PromocodesResponse promocodesResponse = new PromocodesResponse();
        promocodesResponse.setPromocodeId(promocodes.getPromocodeId());
        promocodesResponse.setCode(promocodes.getCode());
        promocodesResponse.setIsActive(promocodes.getIsActive());
        promocodesResponse.setDiscountPercent(promocodes.getDiscountPercent());
        promocodesResponse.setExpiryDate(promocodes.getExpiryDate());

        List<CoursesBasicDto> coursesBasicDtoList = (promocodes.getCoursesList() != null ?
                promocodes.getCoursesList().stream()
                        .map(this::mapToCoursesBasicDto)
                        .collect(Collectors.toList()) : new ArrayList<>());
        promocodesResponse.setApplicableCourses(coursesBasicDtoList);

        return promocodesResponse;
    }

    private CoursesBasicDto mapToCoursesBasicDto(Courses course) {
        CoursesBasicDto coursesBasicDto = new CoursesBasicDto();
        coursesBasicDto.setCourseId(course.getCourseId());
        coursesBasicDto.setCourseName(course.getCourseName());
        coursesBasicDto.setCoursePrice(course.getCoursePrice());
        return coursesBasicDto;
    }
}
