package com.morpho.morphosmart.sdk;

/**
 * @brief Those values are used to define the asynchronous status events returned by the T_MORPHO_CALLBACK_ FUNCTIONS. There are also used to create the binary mask that describes the asynchronous status events that will trig the callback function.
 */
public class CallbackMask {

        /** This asynchronous status event identifies a command status information. */
        public static final int MORPHO_CALLBACK_COMMAND_CMD = 1;
        /** This asynchronous status event identifies a low-resolution image. */
        public static final int MORPHO_CALLBACK_IMAGE_CMD = 2;
        /** This asynchronous status event identifies an enrollment status. */        
        public static final int MORPHO_CALLBACK_ENROLLMENT_CMD = 4;
        /** This asynchronous status event identifies the last image from a live acquisition which is returned in full resolution. */
        public static final int MORPHO_CALLBACK_LAST_IMAGE_FULL_RES_CMD = 8;
        /** This asynchronous status event identifies the status of the quality note of the image detained to be coded. */
        public static final int MORPHO_CALLBACK_CODEQUALITY = 64;
        /** This asynchronous status event identifies the status of the quality note calculated by the "presence detection" function. */
        public static final int MORPHO_CALLBACK_DETECTQUALITY = 128;
        /**
         * Private constructor, do not use.
         */
        private CallbackMask() {
        }

}
