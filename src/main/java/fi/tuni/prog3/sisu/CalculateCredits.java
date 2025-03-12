package fi.tuni.prog3.sisu;

import java.util.List;

/**
 * Class to count students credits from module
 */
public class CalculateCredits {
    /**
     * Checks if course is completed
     * @param id course to be checked
     * @param compCRS completed course id:s of user
     * @return true if copleted
     */
    private static boolean checkCompletedCrs(String id, List<String> compCRS) {
        return compCRS.stream().anyMatch(c -> (c.equals(id)));
    }

    /**
     * recursive function that counts modules credits, and called again for submodules
     * @param mod module to be calculated of credits
     * @param compCRS completed courses, to know what courses will be counted
     * @return returns completed credits from module as int
     */
    private static int calculateCreditsHelp(Module mod, List<String> compCRS) {
        int credits = 0;
        if (mod.getCourses() != null && !mod.getCourses().isEmpty()) {
            for (Course c : mod.getCourses()) {
                if (c.getCreditsMIN() != -1 && checkCompletedCrs(c.getId(), compCRS)) {
                    credits += c.getCreditsMIN();
                }
            }
        }
        if (mod.getSubmodules() != null && !mod.getSubmodules().isEmpty()) {
            for (Module sm : mod.getSubmodules()) {
                int temp = calculateCreditsHelp(sm, compCRS);
                credits += temp;
            }
        }

        return credits;
    }

    // some weird ovwerflow clitch to fix::: NOTE FIXED

    /**
     * First call for recursion
     * @param mod module to be calculated of credits
     * @param compCRS completed courses, to know what courses will be counted
     * @return returns completed credits from module as int
     */
    public static int calculateCredits(Module mod, List<String> compCRS) {
        return calculateCreditsHelp(mod, compCRS);
    }
}