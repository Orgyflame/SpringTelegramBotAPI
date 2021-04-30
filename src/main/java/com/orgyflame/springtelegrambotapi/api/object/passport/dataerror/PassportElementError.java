package com.orgyflame.springtelegrambotapi.api.object.passport.dataerror;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import com.orgyflame.springtelegrambotapi.api.Validable;
import com.orgyflame.springtelegrambotapi.api.object.passport.dataerror.serialization.PassportElementErrorDeserializer;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 * <p>
 * This object represents an error in sent Passport Data that should be resolved by the user. It should be one of
 * <p>
 * PassportElementErrorDataField
 * PassportElementErrorFrontSide
 * PassportElementErrorReverseSide
 * PassportElementErrorSelfie
 * PassportElementErrorFile
 * PassportElementErrorFiles
 * PassportElementErrorUnspecified
 * PassportElementErrorTranslationFile
 * PassportElementErrorTranslationFiles
 */
@JsonDeserialize(using = PassportElementErrorDeserializer.class)
public interface PassportElementError extends Validable, BotApiObject {
}
