import axios from 'axios';

export function storageLogout(){
    localStorage.clear()
}

export function getUserType(urlBase) {
    return axios.get(urlBase+"/auth").then(res => res.data);
}

export function storageNickUsername(nickName){
    localStorage.setItem('nickName', JSON.stringify(nickName))
}

export function getUserName(){
    var storedUserName = localStorage.getItem('nickName')
    storedUserName = JSON.parse(storedUserName);
    return storedUserName
}

