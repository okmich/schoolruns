package com.okmich.common.util.api;

/**
 * This represents the Constant file which defines application wide error
 * message keys.
 *
 * @author Virtusa Corporation
 */
public final class CommonConstants {

    /**
     * Private constructor to prevent instantiation of this class.
     */
    private CommonConstants() {
    }
    /**
     * Key representing an empty string.
     */
    public static final String EMPTY_STRING = "";
    /**
     * index zero.
     */
    public static final int PARAM_INDEX_ZERO = 0;
    /**
     * index ONE.
     */
    public static final int PARAM_INDEX_ONE = 1;
    /**
     * index TWO.
     */
    public static final int PARAM_INDEX_TWO = 2;
    /**
     * index THREE.
     */
    public static final int PARAM_INDEX_THREE = 3;
    /**
     * index FOUR.
     */
    public static final int PARAM_INDEX_FOUR = 4;
    /**
     * index FIVE.
     */
    public static final int PARAM_INDEX_FIVE = 5;
    /**
     * attribute for holding maximum mark.
     */
    public static final int MAXIMUM_MARK = 100;
    /**
     * attribute for holding minimum mark.
     */
    public static final int MINIMUM_MARK = 0;
    /**
     * attribute for holding minus one.
     */
    public static final int MINUS_ONE = -1;
    /**
     * The Constant TERM_GRADE.
     */
    public static final String TERM_GRADE = "TG";
    /**
     * The Constant MONTHLY_GRADE.
     */
    public static final String MONTHLY_GRADE = "MG";
    /**
     * The Constant ABSENT.
     */
    public static final String ABSENT = "AB";
    /**
     * Represents the key for the error message when persist the attendance
     * related data.
     */
    public static final String ERROR_OCCURED_WHILE_LOADING = "Error while loading data -->.";
    /**
     * Represents the key for the log message when began the transform of the
     * scheduler.
     */
    public static final String SCHEDULAR_TRANSFORM_BEGAN = "transform has began working.";
    /**
     * Represents the key for the log message when working the transform of the
     * scheduler.
     */
    public static final Object SCHEDULAR_TRANSFORM_WORKING = "working...";
    /**
     * Represents the key for the log message when error has occurred.
     */
    public static final String SCHEDULAR_TRANSFORM_ERROR = "I/O exception of some sort has occurred.";
    /**
     * Represents the key for the log message when completed the transform of
     * the scheduler.
     */
    public static final String SCHEDULAR_TRANSFORM_COMPLETED = "transform has completed work.";
    /**
     * Represents the key for the log message when began the extract of the
     * scheduler.
     */
    public static final String SCHEDULAR_EXTRACT_BEGAN = "extract has began working.";
    /**
     * Represents the key for the log message when working the extract of the
     * scheduler.
     */
    public static final Object SCHEDULAR_EXTRACT_WORKING = "extract working...";
    /**
     * Represents the key for the log message when copying the file.
     */
    public static final Object SCHEDULAR_EXTRACT_COPIED = "File copied.";
    /**
     * Represents the key for the log message when file not found.
     */
    public static final Object SCHEDULAR_EXTRACT_NOT_FOUND = " in the specified directory.";
    /**
     * Represents the key for the log message when error occurred.
     */
    public static final Object SCHEDULAR_EXTRACT_ERROR = " Error while streaming out the data.";
    /**
     * Represents the key for the log message when completed the extract of the
     * scheduler.
     */
    public static final Object SCHEDULAR_EXTRACT_COMPLETED = "extract has completed.";
    /**
     * Represents the key for the log message when began the loading of the
     * scheduler.
     */
    public static final String SCHEDULAR_LOAD_BEGAN = "load has began working.";
    /**
     * Represents the key for the log message when completed the load of the
     * scheduler.
     */
    public static final String SCHEDULAR_LOAD_COMPLETED = "load successfuly completed.";
    /**
     * Represents the key for the log message when began the clean of the
     * scheduler.
     */
    public static final String SCHEDULAR_CLEAN_BEAGAN = "clean has began working.";
    /**
     * Represents the key for the log message when completed the clean of the
     * scheduler.
     */
    public static final String SCHEDULAR_CLEAN_COMPLETED = "clean successfuly completed.";
    /**
     * Represents the key for the log message when deletion the file.
     */
    public static final Object SCHEDULAR_CLEAN_DELETE_ERROR = "SpradSheet Deletion failed.";
    /**
     * Represents the key for the log message when success the deletion.
     */
    public static final Object SCHEDULAR_CLEAN_DELETE_SUCCESS = "SpradSheet deleted.";
    /**
     * Represents the key for the log message when fail the deletion of the
     * copied file.
     */
    public static final Object SCHEDULAR_CLEAN_COPID_DELETE = "Copied File Deletion failed.";
    /**
     * Represents the key for the log message when success the deletion of the
     * copied file.
     */
    public static final Object SCHEDULAR_CLEAN_COPID_DELETE_SUCCESS = "Copied File deleted.";
    /**
     * Represents the key for the log message when persist of the object of the
     * student is failed.
     */
    public static final Object SCHEDULAR_TRANSFORM_STU_ID = "No record found for this admission number. --->";
    /**
     * Represents the key for the log message when persist of the object of the
     * staff is failed.
     */
    public static final String SCHEDULAR_TRANSFORM_STAFF_ID = "No record found for this registration number. --->";
    /**
     * Represents the key for the log message when error has occurred.
     */
    public static final String ERROR_WHILE_PROCESSING = "Error while processing the data.";
    /**
     * Represents the key for the log message when file hasn't deleted.
     */
    public static final String ROLBACK_FILE_ERROR = "Rollback has happened. File is not deleted --- > ";
    /**
     * Represents the key for the log message when folder hasn't deleted.
     */
    public static final String FOLDER_DELETED = " folder is deleted. --- > ";
    /**
     * Represents the key for the log message when issue in the loading data.
     */
    public static final String DATA_LOADED_ERROR = "Data are not successfully loaded on to the database.";
    /**
     * Represents the key for the log message when directory doesn't exist.
     */
    public static final Object DIRECTORY_DOES_NOT_EXIST = "Directory doesn't exists.";
    /**
     * Represents the key for enable add edit and delete.
     */
    public static final String ENABLE_ADD_EDIT_DELETE = "EnableAddEditDelete";
    /**
     * Represents the id of role admin.
     */
    public static final int ROLE_ADMIN = 1;
    /**
     * Represents the id of role admin.
     */
    public static final int ROLE_SCHOOL_ADMIN = 2;
    /**
     * Represents the id of role teacher.
     */
    public static final int ROLE_TEACHER = 3;
    /**
     * Represents the id of role clerical staff.
     */
    public static final int ROLE_CLERICAL_STAFF = 5;
    /**
     * Represents the id of role parent.
     */
    public static final int ROLE_PARENT = 4;
    /**
     * Constant for holding rank index used in class wise student marks sheet
     * report.
     */
    public static final int RANK_INDEX = 22;
    /**
     * Represents the decimal pattern.
     */
    public static final String ROUND_PATTERN = "###.";
    /**
     * Represents the rounded decimal.
     */
    public static final String ROUNDED_DECIMAL = "#";
    /**
     * Represents the Percentage mark.
     */
    public static final String PERCENTAGE_MARK = " %";
    /**
     * Represents hundred.
     */
    public static final int HUNDRED = 100;
    /**
     * Represents two floating point.
     */
    public static final int FLOAT_POINT_TWO = 2;
    /**
     * Represents Three.
     */
    public static final int THREE = 3;
    /**
     * Represents the key for HALF_DAY.
     */
    public static final String HALF_DAY = "Half Day";
    /**
     * Represents ZERO_POINT_FIVE.
     */
    public static final float ZERO_POINT_FIVE = 0.5f;
    /**
     * SYSTEM_DATE_FORMAT
     */
    public static final String SYSTEM_DATE_FORMAT = "dd/MM/yyyy";
    /**
     * Represents STATUS_ACTIVE.
     */
    public static final String STATUS_ACTIVE = "A";
    /**
     * Represents STATUS_INACTIVE.
     */
    public static final String STATUS_INACTIVE = "I";
    /**
     * Represents STATUS_LOCKED.
     */
    public static final String STATUS_LOCKED = "L";
    /**
     * Represents STATUS_SUSPENDED.
     */
    public static final String STATUS_SUSPENDED = "S";
    /**
     * Represents STATUS_EXPIRED.
     */
    public static final String STATUS_EXPIRED = "E";
    /**
     * Represents STATUS_PENDING.
     */
    public static final String STATUS_PENDING = "P";
    /**
     * Represents STATUS_PUBLISHED.
     */
    public static final String STATUS_PUBLISHED = "P";
    /**
     * Represents CONSTANT_YES.
     */
    public static final String CONSTANT_YES = "Y";
    /**
     * Represents CONSTANT_NO.
     */
    public static final String CONSTANT_NO = "N";
    /**
     * Represents EMPLOYEE_DEFAULT_CATEGORY.
     */
    public static final String EMPLOYEE_DEFAULT_CATEGORY = "T";
    /**
     * Represents EMPLOYEE_DEFAULT_POSITION.
     */
    public static final Integer EMPLOYEE_DEFAULT_POSITION = 4;
    /**
     * Represents EMPLOYEE_DEFAULT_ID_MEANS.
     */
    public static final String EMPLOYEE_DEFAULT_ID_MEANS = "I";
    /**
     * Represents EMPLOYEE_DEFAULT_TYPE.
     */
    public static final String EMPLOYEE_DEFAULT_TYPE = "P";
    /**
     * Represents EMPLOYEE_DEFAULT_STATUS.
     */
    public static final String EMPLOYEE_DEFAULT_STATUS = "A";
    /**
     * Represents PART_CAT_ALL_STAFF.
     */
    public static final char PART_CAT_STAFF = 'A';
    /**
     * Represents PART_CAT_PARENTS.
     */
    public static final char PART_CAT_PARENTS = 'P';
    /**
     * Represents PART_CAT_SCHOOL_ADMIN.
     */
    public static final char PART_CAT_SCHOOL_ADMIN = 'S';
    /**
     * Represents PART_CAT_USER.
     */
    public static final char PART_CAT_USER = 'U';
    /**
     * Represents PART_CAT_CUSTOM.
     */
    public static final char PART_CAT_CUSTOM = 'C';
    /**
     * Represents PART_CAT_TEACHING_STAFF.
     */
    public static final char PART_CAT_TEACHING_STAFF = 'T';
    /**
     * Represents PART_CAT_NON_TEACHING_STAFF.
     */
    public static final char PART_CAT_NON_TEACHING_STAFF = 'N';
    /**
     * Represents MSG_CHNNL_SMS.
     */
    public static final char MSG_CHNNL_SMS = 'S';
    /**
     * Represents MSG_CHNNL_EMAIL.
     */
    public static final char MSG_CHNNL_EMAIL = 'E';
    /**
     * Represents MSG_CHNNL_BOARD.
     */
    public static final char MSG_CHNNL_BOARD = 'B';
    /**
     * Represents CTRL_PARAM_DEFAULT_COUNTRY_KEY.
     */
    public static final String CTRL_PARAM_DEFAULT_COUNTRY_KEY = "DFCNT";
    /**
     * Represents CTRL_PARAM_DEFAULT_PICASA_LOGO_FOLDER.
     */
    public static final String CTRL_PARAM_DEFAULT_PICASA_LOGO_FOLDER = "LGFLD";
    /**
     * Represents CTRL_PARAM_PICASA_SERVICE_USERNAME.
     */
    public static final String CTRL_PARAM_PICASA_SERVICE_USERNAME = "PSUSN";
    /**
     * Represents CTRL_PARAM_PICASA_SERVICE_USERID.
     */
    public static final String CTRL_PARAM_PICASA_SERVICE_USERID = "PSUID";
    /**
     * Represents CTRL_PARAM_PICASA_SERVICE_PASSWD.
     */
    public static final String CTRL_PARAM_PICASA_SERVICE_PASSWD = "PSUPW";
    /**
     * represents EXAM_TYPE_FINAL_EXAM
     */
    public static final String EXAM_TYPE_FINAL_EXAM = "TFE";
    /**
     * represents EXAM_TYPE_ADMISSION_EXAM
     */
    public static final String EXAM_TYPE_ADMISSION_EXAM = "ADM";
    /**
     * represents PAYMENT_MODE_CHEQUE
     */
    public static final String PAYMENT_MODE_CHEQUE = "CHEQUE";
    /**
     * TRAN_SOURCE_ORIGINAL
     */
    public static final String TRAN_SOURCE_ORIGINAL = "1";
    /**
     * TRAN_SOURCE_SYSTEM
     */
    public static final String TRAN_SOURCE_SYSTEM = "2";
}
