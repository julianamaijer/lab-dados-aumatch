import { http } from './config'

export default {

    listar:() => {
        return http.get('pet')
    },
    salvar:(pet) => {
        return http.post('pet', pet)
    },
    atualizar:(animalId, pet) => {
        return http.put('pet/'+ animalId, pet)
    },
    apagar:(animalId) => {
        return http.delete('pet/'+ animalId)
    },
    contar:() => {
        return http.get('pet/total')
    }

}