import axios from "axios";
import { useState } from "react"

function App() {
  const [transactions, setTransactions] = useState([]);

  const fetchTransactions = async () => {
    const response = await axios.get("http://localhost:8080/transacoes");
    // the reponse body comes in 'response.data'
    setTransactions(response.data);
    console.log(response.data);
  }

  return (
    <div>

      <div>
        <h1>Importação de arquivo CNAB</h1>
      </div>
      <div>
        <span>Choose File</span>
        <input type="file" 
        accept=""
        />
        <button>Upload File</button>
      </div>

      <div>
        <h2>Transações</h2>
      </div>
      <ul>
        <li>
          <table>
            <thead>
              <tr>
                <th>Cartão</th>
                <th>CPF</th>
                <th>Data</th>
                <th>Dono da Loja</th>
                <th>Hora</th>
                <th>Nome da Loja</th>
                <th>Tipo</th>
                <th>Valor</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <th>transacao.cartao</th>
                <th>transacao.cpf</th>
                <th>transacao.data</th>
                <th>transacao.donoDaLoja</th>
                <th>transacao.horas</th>
                <th>transacao.nomeDaLoja</th>
                <th>transacao.tipo</th>
                <th>transacao.valor</th>

              </tr>
            </tbody>
          </table>
        </li>
      </ul>



    </div>
  )
 
}

export default App
