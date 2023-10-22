package org.GR8.core;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

import static lombok.AccessLevel.PRIVATE;

@Log4j
@NoArgsConstructor(access = PRIVATE)
public class TestStepLogger {

    public static final String RESET = "\033[0m";  // Text Reset
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE

    public static void logStep(int stepNumber, String message) {
        log.info(BLUE_BOLD + "STEP " + stepNumber + " : " + message + RESET);
    }

    public static void logPreconditionStep(int stepNumber, String message) {
        log.info(GREEN_BOLD + "Precondition STEP " + stepNumber + " : " + message + RESET);
    }

    public static void logPostconditionStep(int stepNumber, String message) {
        log.info(YELLOW_BOLD + "Postcondition STEP " + stepNumber + " : " + message + RESET);
    }

    public static void log(String message) {
        log.info("Additional test information: " + message);
    }


}
