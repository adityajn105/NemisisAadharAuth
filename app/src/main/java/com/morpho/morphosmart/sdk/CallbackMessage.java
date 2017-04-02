package com.morpho.morphosmart.sdk;


public final class CallbackMessage {

        /**
         * Default constructor.
         */
        public CallbackMessage() {
                
        }

        /**
         * Type of the callback message:
         * 1: Command call back.
         * 2: Low resolution image
         * 3: Coded image quality
         * 4: Enrollment Command
         */
        private int messageType;
        /**
         * The object of the callback. can be one of the following:
         * 1: Integer in case of a command call back.
         * 2: byte[] in case of a low resolution image call back.
         * 3: Integer in case of image code quality.
         * 4: byte[]
         *    - [0] : Current Finger number enrolled(1 or 2) 
         *    - [1] : Number of fingers to enroll (1 or 2)
         *    - [2] : Current acquisition number (1 to 3) 
         *    - [3] : Total number of acquisition (3)
         */
        private Object message;

        /** 
         * getter
         * @return the messageType
         */
        public int getMessageType() {
                return messageType;
        }

        /** 
         * setter
         * @param messageType the messageType to set
         */
        public void setMessageType(int messageType) {
                this.messageType = messageType;
        }

        /** getter
         * @return the message
         */
        public Object getMessage() {
                return message;
        }

        /** setter
         * @param message the message to set
         */
        public void setMessage(Object message) {
                this.message = message;
        }
}
