package com.morpho.morphosmart.sdk;
/**
 * 
 * @brief This class store possible values of a detection mode.
 *
 */
public class DetectionMode {

    /** @brief The detection mode is choosen by the scanner. */
    public static final int MORPHO_DEFAULT_DETECT_MODE = 0;
    /** @brief Fast detection mode. */
    public static final int MORPHO_VERIF_DETECT_MODE = 1;
    /** @brief Stronger detection mode. */
    public static final int MORPHO_ENROLL_DETECT_MODE = 2;
    /** @brief Uses a 'led off' presence detection (only on MorphoSmart™; MSOxx1). */
    public static final int MORPHO_WAKEUP_LED_OFF = 4;
    /** @brief The finger must cover an area starting at the top of the image. */
    public static final int MORPHO_FORCE_FINGER_ON_TOP_DETECT_MODE = 16;
    /** @brief Uses a 'led on' presence detection (only on MorphoSmart™; FINGER VP). */
    public static final int MORPHO_WAKEUP_LED_ON = 64;

    /**
     * Private constructor, do not use.
     */
    private DetectionMode() {
    }
}
