package com.ujjwalmaletha.arbnbbackend.listing.application.dto.sub;

import jakarta.validation.constraints.NotNull;

import java.util.Arrays;
import java.util.Objects;

public record PictureDTO(
        @NotNull byte[] file,
        @NotNull String fileContent,
        @NotNull boolean isCover

) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureDTO that = (PictureDTO) o;
        return isCover == that.isCover && Objects.equals(fileContent, that.fileContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileContent, isCover);
    }

    @Override
    public String toString() {
        return "PictureDTO{" +
                "fileContent='" + fileContent + '\'' +
                ", isCover=" + isCover +
                '}';
    }
}
