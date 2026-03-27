package edu.eci.arsw.blueprints.dto;

/**
 * Respuesta genérica uniforme para todos los endpoints de la API REST.
 *
 * @param <T> Tipo de datos en el campo 'data'
 */
public record ApiResponse<T>(int code, String message, T data) {
    
    /**
     * Crea una respuesta exitosa con código 200.
     */
    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    /**
     * Crea una respuesta de creación exitosa con código 201.
     */
    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponse<>(201, message, data);
    }

    /**
     * Crea una respuesta de aceptación con código 202.
     */
    public static <T> ApiResponse<T> accepted(String message, T data) {
        return new ApiResponse<>(202, message, data);
    }

    /**
     * Crea una respuesta de error con código 400.
     */
    public static <T> ApiResponse<T> badRequest(String message, T data) {
        return new ApiResponse<>(400, message, data);
    }

    /**
     * Crea una respuesta de no encontrado con código 404.
     */
    public static <T> ApiResponse<T> notFound(String message, T data) {
        return new ApiResponse<>(404, message, data);
    }
}
