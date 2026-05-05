package com.madsssoft.laadsamaj;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitApiInterface {

    /**
     * SamajApp Api
     **/



    @GET("AllMemberInfo")
    Call<List<PojoMemberInfo>> getAllMemberInfo();

    @GET("member_count")
    Call<PojoMemberCount> getMemberCount();

    @GET("HomeSliderAdd")
    Call<List<PojoAdd>> getAllHomeSliderAdd();

    @GET("LoginSliderAdd")
    Call<List<PojoAdd>> getAllLoginSliderAdd();


    @GET("SamitiInfo")
    Call<List<PojoSamaj>> getAllSamajInfo();

    @GET("GalleryInfoBySamajId/{samajId}")
    Call<List<PojoGallery>> getAllGalleryInfo(@Path("samajId") int samajId);


    @GET("ClientInfoBySamajId/{samajId}")
    Call<List<PojoClient>> getAllClient(@Path("samajId") int samajId);

    @GET("PaperCuttingBySamajId/{samajId}")
    Call<List<PojoPaperCutting>> getAllPaperCuttingInfo(@Path("samajId") int samajId);

    @GET("EventInfo")
    Call<List<PojoEvents>> getAllEventInfo();

    @GET("SliderInfo")
    Call<List<PojoSlider>> getAllSliderInfo();

    @GET("MemberInfoById/{memberId}")
    Call<PojoMemberInfo> getMemberInfoById(@Path("memberId") int memberId);

    @GET("PopupAddInfo")
    Call<PojoAdd> getPopUpAddInfo();

    @GET("AddInfo")
    Call<PojoAdd> getAddInfo();

    @GET("NewsInfo")
    Call<List<PojoNews>> getAllNewsInfo();

    @GET("FrontCategoryInfo")
    Call<List<PojoCategory>> getALlFrontCategoryInfo();

    @GET("CategoryInfo")
    Call<List<PojoCategory>> getAllCategory();

    @GET("RegistrationInfo/{catId}")
    Call<List<PojoRegistrationInfo>> getRegistrationsInfoByCat(@Path("catId") int catId);

    @GET("RegistrationInfoSingle/{Id}")
    Call<PojoRegistrationInfoSingle> getRegistrationInfoById(@Path("Id") int id);

    @GET("RegistrationInfoSearch/{searchString}")
    Call<List<PojoRegistrationInfo>> getRegistrationsInfoBySearch(@Path("searchString") String searchString);

    @GET("RegistrationInfoAllInfoLatest")
    Call<List<PojoRegistrationInfo>> getLatestRegistrationsInfo();

    @GET("RegistrationInfoByMemberId/{memberId}")
    Call<List<PojoRegistrationInfo>> getAllMemberRegistration(@Path("memberId") int memberId);

    @GET("PlacedStudentInfo")
    Call<List<PojoPlacedStudents>> getAllPlaceStudentsInfo();

    @GET("ProfessionInfo")
    Call<List<PojoProfession>> getAllProfession();

    @GET("MemberInfoByProfessionId/{professionId}")
    Call<List<PojoMemberInfo>> getMembersByProfession(@Path("professionId") int professionId);

    @GET("MemberInfoByCityId/{cityId}")
    Call<List<PojoMemberInfo>> getMembersByCity(@Path("cityId") int cityId);

    @GET("MemberInfoByUnMarried/{gender}")
    Call<List<PojoMemberInfo>> getMembersByGender(@Path("gender") String gender);

    @GET("MemberInfoByBloodGroup/{bloodGroup}")
    Call<List<PojoMemberInfo>> getMembersByBloodGroup(@Path("bloodGroup") String bloodGroup);

    @GET("MemberInfoSearch/{searchString}")
    Call<List<PojoMemberInfo>> getMembersBySearch(@Path("searchString") String searchString);

    @GET("DistrictInfo")
    Call<List<PojoDistrict>> getAllDistrict();

    /* ---------------
        POST API
    --------------- */

    @FormUrlEncoded
    @POST("insertMember")
    Call<PojoDefault> registerUser(
            @Field("Gender") String gender,
            @Field("Married_status") String marriedStatus,
            @Field("hideContact") String hideContact, @Field("Profession_id_fk") int professionIdFk,
            @Field("District_id_fk") int districtIdFk,
            @Field("Blood_group") String bloodGroup,
            @Field("Name") String name,
            @Field("Father") String father,
            @Field("Mother") String mother,
            @Field("Ocupation") String occupation,
            @Field("Dob") String dob,
            @Field("Mobile_no") String mobileNo,
            @Field("Whatsapp_no") String whatsappNo,
            @Field("Email") String email,
            @Field("Wife_name") String wifeName,
            @Field("Father_in_law") String fatherInLaw,
            @Field("Mother_in_law") String motherInLaw,
            @Field("No_of_children") int numberOfChildren,
            @Field("Doa") String doa,
            @Field("Facebook_link") String facebookLink,
            @Field("Instagram_link") String instagramLink,
            @Field("Youtube_link") String youtubeLink,
            @Field("Address") String address,
            @Field("Password") String password,
            @Field("Live_location") String liveLocation,
            @Field("Name_of_children") String nameOfChildren,
            @Field("Image") String image1,
            @Field("Image1") String image2
    );

@FormUrlEncoded
    @POST("updateMemberInfo")
    Call<PojoDefault> updateMemberInfo(
        @Field("Id") int id,
        @Field("Gender") String gender,
        @Field("Married_status") String marriedStatus,
        @Field("hideContact") String hideContact,
        @Field("Profession_id_fk") int professionIdFk,
        @Field("District_id_fk") int districtIdFk,
        @Field("Blood_group") String bloodGroup,
        @Field("Name") String name,
        @Field("Father") String father,
        @Field("Mother") String mother,
        @Field("Ocupation") String occupation,
        @Field("Dob") String dob,
        @Field("Whatsapp_no") String whatsappNo,
        @Field("Email") String email,
        @Field("Wife_name") String wifeName,
        @Field("Father_in_law") String fatherInLaw,
        @Field("Mother_in_law") String motherInLaw,
        @Field("No_of_children") int numberOfChildren,
        @Field("Doa") String doa,
        @Field("Facebook_link") String facebookLink,
        @Field("Instagram_link") String instagramLink,
        @Field("Youtube_link") String youtubeLink,
        @Field("Address") String address,
        @Field("Live_location") String liveLocation,
        @Field("Name_of_children") String nameOfChildren,
        @Field("Image") String image1,
        @Field("Image1") String image2
    );


    @FormUrlEncoded
    @POST("updateMemberPassword")
    Call<PojoDefault> changePassword(
            @Field("Id") int regId,
            @Field("Password") String password
    );
    @FormUrlEncoded
    @POST("checkMemberLogin")
    Call<PojoLogin> checkLogin(
            @Field("Mobile_no") String mobile_no,
            @Field("Password") String password
    );
    @FormUrlEncoded
    @POST("InsertFeedback")
    Call<PojoFeedbackResponse> insertFeedback(
            @Field("Name") String Name,
            @Field("Mobile") String Mobile,
            @Field("Subject") String Subject,
            @Field("Message") String Message
    );


    @FormUrlEncoded
    @POST("insertRegistation")
    Call<PojoDefault> insertRegistration(
            @Field("member_id_fk") int member_id_fk,
            @Field("Category_id_fk") int categoryId,
            @Field("Owner_name") String ownerName,
            @Field("Company_name") String companyName,
            @Field("Address") String address,
            @Field("Whatsapp_no") String whatsappNo,
            @Field("Mobile") String mobile,
            @Field("Website_link") String websiteLink,
            @Field("Product") String product,
            @Field("Email") String email,
            @Field("live_location") String live_location,
            @Field("Facebook_link") String facebookLink,
            @Field("Instagram_link") String instagramLink,
            @Field("Youtube_link") String youtubeLink,
            @Field("About") String about,
            @Field("Image") String image1,
            @Field("Image1") String image2
    );


    @FormUrlEncoded
    @POST("updateRegistationInfo")
    Call<PojoDefault> updateRegistrationInfo(
            @Field("Id") int regId,
            @Field("Category_id_fk") int categoryId,
            @Field("Owner_name") String ownerName,
            @Field("Company_name") String companyName,
            @Field("Address") String address,
            @Field("Whatsapp_no") String whatsappNo,
            @Field("Website_link") String websiteLink,
            @Field("Product") String product,
            @Field("Email") String email,
            @Field("live_location") String live_location,
            @Field("Facebook_link") String facebookLink,
            @Field("Instagram_link") String instagramLink,
            @Field("Youtube_link") String youtubeLink,
            @Field("About") String about,
            @Field("Image") String image1,
            @Field("Image1") String image2
    );

    /* Member Relation Related API */

    @GET("MemberRelativeInfoById/{memberID}")
    Call<List<PojoRelativeInfo>> getMemberRelativeInfo(@Path("memberID") String memberID);

    @GET("RelativeMemberInfoById/{memberID}")
    Call<List<PojoRelativeInfo>> getAllRequests(@Path("memberID") String memberID);




    @FormUrlEncoded
    @POST("insertRelative")
    Call<PojoDefault> insertRequestForRelative(
            @Field("Member_id_fk") int Member_id_fk,
            @Field("Relative_id_fk") int Relative_id_fk,
            @Field("Relation_with_relative") String Relation_with_relative,
            @Field("Relative_relation_you") String Relative_relation_you
    );

    @FormUrlEncoded
    @POST("updateRelationStatus")
    Call<PojoDefault> insertResponseForRequest(
            @Field("Id") String Id,
            @Field("Member_id_fk") int Member_id_fk,
            @Field("Relative_id_fk") int Relative_id_fk,
            @Field("Relation_with_relative") String Relation_with_relative,
            @Field("Relative_relation_you") String Relative_relation_you,
            @Field("Type") String type
    );


}
