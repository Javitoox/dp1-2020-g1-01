export function storageUserType(userType) {
    localStorage.setItem('userType', JSON.stringify(userType))
}

export function storageLogout(){
    localStorage.clear()
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

export function storageNickUsername(nickName){
    localStorage.setItem('nickName', JSON.stringify(nickName))
}

export function getUserName(){
    var storedUserName = localStorage.getItem('nickName')
    storedUserName = JSON.parse(storedUserName);
    return storedUserName
}

