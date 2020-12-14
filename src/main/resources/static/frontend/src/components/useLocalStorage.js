import {useState} from "react";

/*
Guardamos un dato que persistirá en nuestra aplicación.
Lo que se crea aquí a través del hook de useState es un hook personalizado a nuestra necesidad
de persistir datos en nuestra aplicación.
*/
export function useLocalStorage(key, initialValue) {
    const [storedValue, setStoredValue] = useState(() => {
        try{
            const item = window.localStorage.getItem(key)
            return item ? JSON.parse(item) : initialValue
        }catch(error){
            return initialValue
        }
    })

    const setValue = value => {
        try{
            setStoredValue(value)
            window.localStorage.setItem(key, JSON.stringify(value))
        }catch(error){
            console.error(error)
        }
    }
    return [storedValue, setValue]
}