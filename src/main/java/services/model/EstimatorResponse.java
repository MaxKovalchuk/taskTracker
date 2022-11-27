package services.model;

import java.util.Objects;

public class EstimatorResponse {

    private int estimate;

    public EstimatorResponse() {
    }

    public EstimatorResponse(int estimate) {
        this.estimate = estimate;
    }

    public int getEstimate() {
        return estimate;
    }

    public void setEstimate(int estimate) {
        this.estimate = estimate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstimatorResponse that = (EstimatorResponse) o;
        return estimate == that.estimate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(estimate);
    }

    @Override
    public String toString() {
        return "EstimatorResponse{" +
                "estimate=" + estimate +
                '}';
    }
}
