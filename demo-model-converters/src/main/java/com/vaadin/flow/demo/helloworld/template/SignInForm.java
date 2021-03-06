/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.demo.helloworld.template;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.demo.helloworld.template.SignInForm.SignInModel;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.Encode;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * The one and only view in the SignIn application.
 */
@Tag("sign-in-form")
@HtmlImport("frontend://components/SignInForm.html")
@Route("")
public class SignInForm extends PolymerTemplate<SignInModel> {

    /**
     * The template model.
     */
    public interface SignInModel extends TemplateModel {

        /**
         * Gets the name of a registrant.
         *
         * @return the name of a registrant
         */
        String getName();

        /**
         * Sets the name of a registrant.
         *
         * @param name
         *            the name of a registrant
         */
        void setName(String name);

        /**
         * Social security number. It has {@link Long} type and is supposed to
         * be stored as a {@link Long} in a database.
         * <p>
         * Flow doesn't support {@link Long} since this type doesn't exist on
         * the client side. So it should be converted to some supported type.
         *
         * @param ssd
         *            the social security number of a registrant
         */
        @Encode(LongToStringConverter.class)
        void setSsd(Long ssd);

        /**
         * Gets the name of a registrant.
         *
         * @return the social sceurity number of a registrant
         */
        Long getSsd();

        /**
         * Returns the date of birth.
         * <p>
         * One {@link Date} value will be used in 3 different input fields: a
         * separate day field, a month field and an year field. So converter is
         * used here to convert one value into a bean with 3 properties.
         *
         * @param date
         *            the date of birth
         */
        @Encode(DateToDateBeanConverter.class)
        void setBirthDate(Date date);

        /**
         * Gets the the date of birth.
         *
         * @return the date of birth
         */
        Date getBirthDate();

        /**
         * Sets the registration resulting message.
         *
         * @param msg
         *            a message
         */
        void setRegistrationMessage(String msg);
    }

    /**
     * Creates a new instance of the sign in form.
     */
    public SignInForm() {
        setId("template");
        getModel().setBirthDate(new Date());
    }

    @EventHandler
    private void signIn() {
        String name = getModel().getName();
        Long ssd = getModel().getSsd();
        Date date = getModel().getBirthDate();
        Logger.getLogger(SignInForm.class.getName())
                .info("Register a new user with the name '" + name
                        + "; and  SSD  '" + ssd + "', and birthday: " + date);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);

        LocalDate birthDay = calendar.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
        getModel().setRegistrationMessage("Welcome " + name + ", your are "
                + Period.between(birthDay, LocalDate.now()).getYears()
                + " years old");
    }
}
