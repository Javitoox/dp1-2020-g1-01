export function storageUserType(userType) {
    localStorage.setItem('userType', JSON.stringify(userType))
}

export function getUserType() {
    var storedType = localStorage.getItem('userType')
    if (storedType === null) {
        storedType = "usuario";
    } else {
        storedType = JSON.parse(storedType);
    }
    return storedType
}