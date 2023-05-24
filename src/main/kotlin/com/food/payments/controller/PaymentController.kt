package com.food.payments.controller

import com.food.payments.dto.PaymentDto
import com.food.payments.service.PaymentService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/pagamentos")
class PaymentController(
    private val service: PaymentService
) {

    @GetMapping
    fun getAllPayments(@PageableDefault(size = 10) page: Pageable): Page<PaymentDto> {
        return service.getAllPayments(page)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<PaymentDto> {
        val payment = service.getById(id)
        return ResponseEntity.ok(payment)
    }

    @PostMapping
    fun create(@RequestBody @Valid paymentDto: PaymentDto,
               uriBuilder: UriComponentsBuilder
    ): ResponseEntity<PaymentDto> {
        val payment = service.create(paymentDto)
        val uri = uriBuilder.path("/pagamentos/{id}").buildAndExpand(payment.id).toUri()
        
        return ResponseEntity.created(uri).body(payment)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable @RequestBody @Valid id: Long, paymentDto: PaymentDto): ResponseEntity<PaymentDto> {
        val newPaymentDto = service.update(id, paymentDto)

        return  ResponseEntity.ok(newPaymentDto)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<PaymentDto> {
        service.delete(id)

        return ResponseEntity.noContent().build()
    }

}