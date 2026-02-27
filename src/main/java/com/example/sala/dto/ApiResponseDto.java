/** Clasă pentru transferul datelor între front-end și back-end, folosind un obiect DTO.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */

package com.example.sala.dto;

public class ApiResponseDto {
    private String message;

    public ApiResponseDto() {}
    public ApiResponseDto(String message) { this.message = message; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

