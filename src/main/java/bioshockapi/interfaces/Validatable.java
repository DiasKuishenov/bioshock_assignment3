package bioshockapi.interfaces;

import bioshockapi.exception.InvalidInputException;

public interface Validatable {
    void validate() throws InvalidInputException;
}