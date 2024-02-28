import axios from "axios";

const api = "http://localhost:8080/api/book/"

const saveBook = async (bookData) => {
    try {
        const response = await axios.post(api, bookData, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return response;
    } catch (error) {
        console.error("Error al enviar la solicitud:", error);
        if (error.response) {
            console.error("Respuesta del servidor:", error.response.data);
        }
        throw error;
    }
};

const updateBook = async (bookData) => {
    try {
        const response = await axios.put(api, bookData, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return response;
    } catch (error) {
        console.error("Error al enviar la solicitud:", error);
        if (error.response) {
            console.error("Respuesta del servidor:", error.response.data);
        }
        throw error;
    }
};

const getBooks = async (params) => {
    try {
        const pagination = params ? `?sort=${params?.sort || "id"},${params?.direction || "asc"}` : '';
        const response = await axios.get(`${api}paged/${pagination}`);
        return response.data;
    } catch (error) {
        throw error;
    }
};

const getBooksCover = async (params) => {
    try {
        const pagination = params ? `?sort=${params?.sort || "id"},${params?.direction || "asc"}` : '';
        const response = await axios.get(`${api}paged/covered/${pagination}`);
        return response.data;
    } catch (error) {
        throw error;
    }
};

const getBook = async (bookId) => {
    try {
        const response = await axios.get(api + bookId)
        return response.data;
    } catch (error) {
        console.log("Obtener uno fallo!", error);
        throw error;
    }
}

const deleteBook = async (bookId) => {
    try {
        const response = await axios.delete(api + bookId)
        return response.data;
    } catch (error) {
        console.log("Change status fallo!", error);
        throw error;
    }
}

export default {
    getBooks,
    getBooksCover,
    getBook,
    saveBook,
    updateBook,
    deleteBook
}