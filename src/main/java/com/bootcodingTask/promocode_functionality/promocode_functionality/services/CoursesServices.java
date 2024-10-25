package com.bootcodingTask.promocode_functionality.promocode_functionality.services;

import com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.basic.PromocodesBasicDto;
import com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.reponse.CoursesResponse;
import com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.request.CoursesRequests;
import com.bootcodingTask.promocode_functionality.promocode_functionality.entity.Courses;
import com.bootcodingTask.promocode_functionality.promocode_functionality.entity.Promocodes;
import com.bootcodingTask.promocode_functionality.promocode_functionality.repository.CoursesRepository;
import com.bootcodingTask.promocode_functionality.promocode_functionality.repository.PromocodesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CoursesServices {

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private PromocodesRepository promocodesRepository;

    public CoursesResponse saveCourses(CoursesRequests coursesRequests) {
        Courses courses = new Courses();
        courses.setCourseName(coursesRequests.getCourseName());
        courses.setCoursePrice(coursesRequests.getCoursePrice());

        if (coursesRequests.getPromocodeId() != null) {
            Optional<Promocodes> promocode = promocodesRepository.findById(Long.parseLong(coursesRequests.getPromocodeId()));
            promocode.ifPresent(courses::setPromocodes);
        }

        courses = coursesRepository.save(courses);
        return mapToCoursesResponse(courses);
    }

    public CoursesResponse getCourseById(Long courseId) {
        return coursesRepository.findById(courseId)
                .map(this::mapToCoursesResponse)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
    }

    public CoursesResponse updateCourse(Long courseId, CoursesRequests coursesRequests) {
        Courses existingCourse = coursesRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        existingCourse.setCourseName(coursesRequests.getCourseName());
        existingCourse.setCoursePrice(coursesRequests.getCoursePrice());

        if (coursesRequests.getPromocodeId() != null) {
            Optional<Promocodes> promocode = promocodesRepository.findById(Long.parseLong(coursesRequests.getPromocodeId()));
            promocode.ifPresent(existingCourse::setPromocodes);
        } else {
            existingCourse.setPromocodes(null);
        }

        Courses updatedCourse = coursesRepository.save(existingCourse);
        return mapToCoursesResponse(updatedCourse);
    }

    public void deleteCourse(Long courseId) {
        if (!coursesRepository.existsById(courseId)) {
            throw new RuntimeException("Course not found with id: " + courseId);
        }
        coursesRepository.deleteById(courseId);
    }

    public List<CoursesResponse> searchCoursesByName(String courseName) {
        List<Courses> courses = coursesRepository.findByCourseName(courseName);
        return courses.stream()
                .map(this::mapToCoursesResponse)
                .collect(Collectors.toList());
    }

    // Only one getAllCourses method with pagination
    public Page<CoursesResponse> getAllCourses(Pageable pageable) {
        return coursesRepository.findAll(pageable).map(this::mapToCoursesResponse);
    }

    private CoursesResponse mapToCoursesResponse(Courses course) {
        CoursesResponse coursesResponse = new CoursesResponse();
        coursesResponse.setCourseId(course.getCourseId());
        coursesResponse.setCourseName(course.getCourseName());
        coursesResponse.setCoursePrice(course.getCoursePrice());

        if (course.getPromocodes() != null && course.getPromocodes().getDiscountPercent() != null) {
            BigDecimal discount = course.getCoursePrice()
                    .multiply(BigDecimal.valueOf(course.getPromocodes().getDiscountPercent()))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            coursesResponse.setDiscountedCoursePrice(course.getCoursePrice().subtract(discount));
        } else {
            coursesResponse.setDiscountedCoursePrice(course.getCoursePrice());
        }

        if (course.getPromocodes() != null) {
            PromocodesBasicDto promocodesBasicDto = new PromocodesBasicDto();
            promocodesBasicDto.setPromocodeId(course.getPromocodes().getPromocodeId());
            promocodesBasicDto.setCode(course.getPromocodes().getCode());
            promocodesBasicDto.setDiscountPercent(course.getPromocodes().getDiscountPercent());

            coursesResponse.setPromocodesBasicDto(promocodesBasicDto);
        }

        return coursesResponse;
    }
}
