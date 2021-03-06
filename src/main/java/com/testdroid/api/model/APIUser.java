package com.testdroid.api.model;

import com.testdroid.api.APIEntity;
import static com.testdroid.api.APIEntity.createUri;
import com.testdroid.api.APIException;
import com.testdroid.api.APIListResource;
import com.testdroid.api.APIQueryBuilder;
import com.testdroid.api.APISort;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Łukasz Kajda <lukasz.kajda@bitbar.com>
 */
@XmlRootElement
public class APIUser extends APIEntity {
    
    private String email;
    private String name;
    private String state;
    private String country;
    private String city;
    private String code;
    private String address;
    private String phone;
    private String organization;
    private String vatID;
    private String timeZone;
    private boolean enabled;
    private APIRole[] roles;

    public APIUser() {}
    public APIUser(Long id, String email, String name, String state, String country, String city, String code, String address, String phone, 
            String organization, String vatID, String timeZone, boolean enabled, APIRole... roles) {
        super(id);
        this.email = email;
        this.name = name;
        this.state = state;
        this.country = country;
        this.city = city;
        this.code = code;
        this.address = address;
        this.phone = phone;
        this.organization = organization;
        this.vatID = vatID;
        this.timeZone = timeZone;
        this.enabled = enabled;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getVatID() {
        return vatID;
    }

    public void setVatID(String vatID) {
        this.vatID = vatID;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public APIRole[] getRoles() {
        return roles;
    }

    public void setRoles(APIRole[] roles) {
        this.roles = roles;
    }
    
    private String getProjectsURI() { return createUri(selfURI, "/projects"); }
    private String getProjectURI(Long id) { return createUri(selfURI, "/projects/" + id); }
    private String getDeviceGroupsURI() { return createUri(selfURI, "/device-groups"); }
    private String getNotificationsURI() { return createUri(selfURI, "/notifications"); }
    private String getNotificationURI(long id) { return createUri(selfURI, "/notifications/" + id); }
    private String getAvailbleProjectTypesURI() { return createUri(selfURI, "/available-project-types"); }
        
    private String getCreateNotificationParams(String email, APINotificationEmail.Type type) {
        return String.format("email=%s&type=%s", email, type);
    }
    private Map<String, Object> getUpdateUserParams(final String address, final String city, final String code, final String country, final String email, 
            final String name, final String organization, final String phone, final String state, final String timeZone, final String vatId) {
        return new HashMap<String, Object>()
        {{
            put("address", address);
            put("city", city);
            put("country", country);
            put("email", email);
            put("name", name);
            put("organization", organization);
            put("phone", phone);
            put("state", state);
            put("timeZone", timeZone);
            put("vatId", vatId);
        }};
    }
        
    public APIProject createProject(APIProject.Type type) throws APIException {
        return postResource(getProjectsURI(), String.format("type=%s", type.name()), APIProject.class);
    }
    
    public APIProject createProject(APIProject.Type type, String name) throws APIException {
        return postResource(getProjectsURI(), String.format("type=%s&name=%s", type.name(), name), APIProject.class);
    }
    
    public void update() throws APIException {
        APIUser user = postResource(selfURI, getUpdateUserParams(address, city, code, country, email, name, organization, phone, state, timeZone, vatID), APIUser.class);
        clone(user);
    }
    
    @JsonIgnore
    public APIListResource<APIProject> getProjectsResource() throws APIException {
        return getListResource(getProjectsURI(), APIProject.class);
    }
    
    /**
     * @since 1.3.34
     * @param queryBuilder
     * @return
     * @throws APIException 
     */
    @JsonIgnore
    public APIListResource<APIProject> getProjectsResource(APIQueryBuilder queryBuilder) throws APIException {
        return getListResource(getProjectsURI(), queryBuilder, APIProject.class);
    }
    
    /**
     * @deprecated 
     * @param offset
     * @param limit
     * @param search
     * @param sort
     * @return
     * @throws APIException 
     */
    @JsonIgnore
    public APIListResource<APIProject> getProjectsResource(long offset, long limit, String search, APISort sort) throws APIException {
        return getListResource(getProjectsURI(), offset, limit, search, sort, APIProject.class);
    }
    
    public APIProject getProject(Long id) throws APIException {
        return getResource(getProjectURI(id), APIProject.class).getEntity();
    }
    
    @JsonIgnore
    public APIListResource<APIDeviceGroup> getDeviceGroupsResource() throws APIException {
        return getListResource(getDeviceGroupsURI(), APIDeviceGroup.class);
    }
    
    /**
     * @since 1.3.34
     * @param queryBuilder
     * @return
     * @throws APIException 
     */
    @JsonIgnore
    public APIListResource<APIDeviceGroup> getDeviceGroupsResource(APIQueryBuilder queryBuilder) throws APIException {
        return getListResource(getDeviceGroupsURI(), queryBuilder, APIDeviceGroup.class);
    }
    
    /**
     * @deprecated
     * @param offset
     * @param limit
     * @param search
     * @param sort
     * @return
     * @throws APIException 
     */
    @JsonIgnore
    public APIListResource<APIDeviceGroup> getDeviceGroupsResource(long offset, long limit, String search, APISort sort) throws APIException {
        return getListResource(getDeviceGroupsURI(), offset, limit, search, sort, APIDeviceGroup.class);
    }
    
    @JsonIgnore
    public APIDeviceGroup createDeviceGroup(String name, APIDevice.OsType osType) throws APIException {
        return postResource(getDeviceGroupsURI(), String.format("name=%s&osType=%s", name, osType), APIDeviceGroup.class);
    }
    
    @JsonIgnore
    public APINotificationEmail createNotificationEmail(String email, APINotificationEmail.Type type) throws APIException {
        return postResource(getNotificationsURI(), getCreateNotificationParams(email, type), APINotificationEmail.class);
    }

    @JsonIgnore
    public APIListResource<APINotificationEmail> getNotificationEmails() throws APIException {
        return getListResource(getNotificationsURI(), APINotificationEmail.class);
    }

    /**
     * @since 1.3.34
     * @param queryBuilder
     * @return
     * @throws APIException 
     */
    @JsonIgnore
    public APIListResource<APINotificationEmail> getNotificationEmails(APIQueryBuilder queryBuilder) throws APIException {
        return getListResource(getNotificationsURI(), queryBuilder, APINotificationEmail.class);
    }
    
    /**
     * @deprecated 
     * @param offset
     * @param limit
     * @param search
     * @param sort
     * @return
     * @throws APIException 
     */
    @JsonIgnore
    public APIListResource<APINotificationEmail> getNotificationEmails(long offset, long limit, String search, APISort sort) throws APIException {
        return getListResource(getNotificationsURI(), offset, limit, search, sort, APINotificationEmail.class);
    }
    
    @JsonIgnore
    public APINotificationEmail updateNotificationEmail(long id, APINotificationEmail.Type type) throws APIException {
        return postResource(getNotificationURI(id), String.format("type=%s", type), APINotificationEmail.class);
    }
    
    @JsonIgnore
    public void deleteNotificationEmail(long id) throws APIException {
        deleteResource(getNotificationURI(id));
    }

    @Override
    @JsonIgnore
    protected <T extends APIEntity> void clone(T from) {
        APIUser apiUser = (APIUser) from;
        cloneBase(from);
        this.address = apiUser.address;
        this.city = apiUser.city;
        this.code = apiUser.code;
        this.country = apiUser.country;
        this.email = apiUser.email;
        this.enabled = apiUser.enabled;
        this.name = apiUser.name;
        this.organization = apiUser.organization;
        this.phone = apiUser.phone;
        this.roles = apiUser.roles;
        this.state = apiUser.state;
        this.timeZone = apiUser.timeZone;
        this.vatID = apiUser.vatID;
    }
}
