package ru.practicum.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class NewUserDto {
    @NotBlank(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 250, message = "Имя должно содержать не менее 2 символов и не более 250")
    private String name;
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email введен некорректно")
    @Size(min = 6, max = 254, message = "Email должен быть минимум из 6 символов и не более 254")
    private String email;
}
