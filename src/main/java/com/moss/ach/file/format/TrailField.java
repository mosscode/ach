package com.moss.ach.file.format;

/**
 * Created by todorsusnjevic on 10/23/15.
 */
public class TrailField extends AbstractStringField {

    public static final String TRAIL_LINE = "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999";

    public int getLength() {
        return TRAIL_LINE.length();
    }

    public RequirementType getRequirementType() {
        return RequirementType.MANDATORY;
    }
}
