<template>
  <div id="app">

    <nav>
      <div class="nav-wrapper blue darken-1">
        <a href="#" class="brand-logo center">AuMatch - Adoção de animais</a>
      </div>
    </nav>

    <div class="container">

      <form @submit.prevent="salvar">

          <label>Nome</label>
          <input type="text" placeholder="Nome" v-model="pet.nome">
          <label>Porte</label>
          <input type="text" placeholder="Porte" v-model="pet.porte">
          <label>Idade</label>
          <input type="number" placeholder="Idade" v-model="pet.idade">
          <label>Sexo</label>
          <input type="text" placeholder="Sexo" v-model="pet.sexo">
          <label>Tipo</label>
          <input type="text" placeholder="Tipo" v-model="pet.tipo">
          <label>Status</label>
          <input type="text" placeholder="Status" v-model="pet.status">

          <button class="waves-effect waves-light btn-small">Salvar<i class="material-icons left">save</i></button>

      </form>
      <table>

        <thead>

          <tr>
            <th>ID</th>
            <th>NOME</th>
            <th>PORTE</th>
            <th>IDADE</th>
            <th>SEXO</th>
            <th>TIPO</th>
            <th>STATUS</th>
            <th>OPÇÕES</th>
          </tr>

        </thead>

        <tbody>

          <tr v-for="pet of pets" :key="pet.animalId">

            <td>{{ pet.animalId }}</td>
            <td>{{ pet.nome }}</td>
            <td>{{ pet.porte }}</td>
            <td>{{ pet.idade }}</td>
            <td>{{ pet.sexo }}</td>
            <td>{{ pet.tipo }}</td>
            <td>{{ pet.status }}</td>
            <td>
              <button @click="editar(pet)" class="waves-effect btn-small blue darken-1"><i class="material-icons">create</i></button>
              <button @click="remover(pet)" class="waves-effect btn-small red darken-1"><i class="material-icons">delete_sweep</i></button>

            </td>

          </tr>

        </tbody>

      </table>
          <button @click="contar()" class="waves-effect waves-light btn-small">Contar total<i class="material-icons center">contar</i></button>
    </div>

  </div>
</template>

<script>
import Animal from './service/animais'
export default {

    data(){
        return {
            pets: [],
            pet: {
                animalId: '',
                nome: '',
                porte: '',
                idade: '',
                sexo: '',
                tipo: '',
                status: ''
                }
        }
    },

    mounted(){
        this.listar();
    },

    methods: {
        listar(){
            Animal.listar().then(resposta => {
                this.pets = resposta.data
            })
        },
        salvar(){
            if(!this.pet.animalId){
                Animal.salvar(this.pet).then( ()=> {
                    this.pet = {}
                    alert('Salvo com sucesso!')
                    this.listar()
                })
            }else{
                Animal.atualizar(this.pet.animalId, this.pet).then( ()=> {
                    this.pet = {}
                    alert('Atualizado com sucesso!')
                    this.listar()
                })
            }
        },
        editar(pet){
            this.pet = pet
        },
        remover(pet){
        if(confirm('Deseja excluir o produto?')){
            Animal.apagar(pet.animalId).then(() => {
                this.listar();
            })
        }
        },
        contar(){
            Animal.contar().then(resposta => {
                alert('Total de registros: ' + resposta.data)
            })
        }
    }
}
</script>