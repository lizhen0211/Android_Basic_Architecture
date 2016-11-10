package com.architecture.util.net;

/**
 * 错误实体类
 *
 */
public class UnprocessableEntity {

    String message;

    Error[] errors;

    public Error[] getErrors() {
        return errors;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        if (errors != null) {
            StringBuilder sb = new StringBuilder();
            for (Error err : errors) {
                sb.append(err.toString()).append(",");
            }

            sb.delete(sb.length() - 1, sb.length());

            return String.format("message : %s, Errors : %s", message, sb.toString());

        } else {
            return String.format("message : %s, Errors : ", message);
        }
    }

    public static class Error {
        // 字段错误
        String field;
        // 资源错误
        String resource;
        // 权限错误
        String permission;
        // 错误码
        String code;

        public boolean isFieldError() {
            if (null != field) {
                return true;
            }
            return false;
        }

        public boolean isResourceError() {
            if (null != resource) {
                return true;
            }
            return false;
        }

        public boolean isPermissionError() {
            if (null != permission) {
                return true;
            }
            return false;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            if (isFieldError()) {
                return String.format("%s, %s", field, code);
            } else if (isResourceError()) {
                return String.format("%s, %s", resource, code);
            } else {
                return "";
            }
        }
    }
}
