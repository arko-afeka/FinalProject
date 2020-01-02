package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.apache.commons.lang3.CharUtils;

import java.nio.CharBuffer;

public class TagOpenState extends State {
    protected TagOpenState(StringBuffer data) {
        super(data);
    }

    @Override
    void run() {
        char cur = data.charAt();
        switch (cur) {
            case (SpecialChar.BANG): {
                break;
            }
            case (SpecialChar.SLASH): {
                break;
            }
            case (SpecialChar.QUESTION): {
                break;
            }
            case (SpecialChar.PERCENT): {
                break;
            }
            case (SpecialChar.NULL): {
                break;
            }
            default: {
                if (CharUtils.isAsciiAlpha(cur)) {
                    data.trimToSize();
                } else {
                    token =
                }
                break;
            }
        }
    }
}
