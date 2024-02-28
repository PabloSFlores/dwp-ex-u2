const blobToBase64 = async (file, mimeType) => {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
            const base64data = reader.result.replace(/^data:(.*?);base64,/, `data:${mimeType};base64,`);
            resolve(base64data);
        };
        reader.onerror = (error) => {
            reject(error);
        };
    });
};

const base64toFile = (dataurl, filename) => {
    var arr = dataurl.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[arr.length - 1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, { type: mime });
}

export default { blobToBase64, base64toFile }