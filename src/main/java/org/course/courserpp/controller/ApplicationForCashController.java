package org.course.courserpp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.request.ApplicationForCashRequestDto;
import org.course.courserpp.dto.response.ApplicationForCashResponseDto;
import org.course.courserpp.service.ApplicationForCashService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/applications-for-cash")
@RequiredArgsConstructor
public class ApplicationForCashController {

    private final ApplicationForCashService applicationForCashService;

    @GetMapping
    public ResponseEntity<List<ApplicationForCashResponseDto>> findAll() {
        return ResponseEntity.ok(applicationForCashService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationForCashResponseDto> findById(@PathVariable String id) {
        return ResponseEntity.ok(applicationForCashService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ApplicationForCashResponseDto> create(
            @Valid @RequestBody ApplicationForCashRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationForCashService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationForCashResponseDto> update(@PathVariable String id,
                                                                 @Valid @RequestBody ApplicationForCashRequestDto dto) {
        return ResponseEntity.ok(applicationForCashService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        applicationForCashService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
