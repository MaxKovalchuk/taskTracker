package services.model;

import java.util.Objects;

public class SaveKeyRequest {

    private String text;
    private int complexity;

    public SaveKeyRequest() {
    }

    public SaveKeyRequest(
            String text,
            int complexity
    ) {
        this.text = text;
        this.complexity = complexity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaveKeyRequest that = (SaveKeyRequest) o;
        return complexity == that.complexity && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, complexity);
    }

    @Override
    public String toString() {
        return "EstimatorRequest{" +
                "text='" + text + '\'' +
                ", complexity=" + complexity +
                '}';
    }
}
