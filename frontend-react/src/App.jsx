/* eslint-disable no-unused-vars */
/* eslint-disable react/jsx-key */
import axios from "axios";
import { useEffect, useState } from "react";

function App() {
  // setTransactions update transactions state
  const [transactions, setTransactions] = useState([]);
  const [file, setFile] = useState(null);

  const fetchTransactions = async () => {
    try {
      const response = await axios.get("http://localhost:8080/operacoes");
      let currentTransaction = response.data;
      setTransactions(currentTransaction);
      console.log(response.data);
    } catch (error) {
      console.error("Error fetching transactions:", error);
    }
  };

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const uploadFile = async () => {
    try {
      const formData = new FormData();
      // Append the current state file to the FormData
      formData.append("file", file);
      // Use Axios to send a POST request with the FormData
      await axios.post("http://localhost:8080/cnab/upload", formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
  
      // Optionally, refresh transactions after a successful upload
      // fetchTransactions();
    } catch (error) {
      console.error("Error uploading file:", error);
    }
  };

  useEffect(() => {
    fetchTransactions();
  }, []);

  return (
    <div>
      <div>
        <h1>Importação CNAB</h1>
      </div>

      <div>
        <span>Choose File</span>
        <input
            type="file"
            accept=".txt"
            // loading file in current component state with onChange={handleFileChange}
            onChange={handleFileChange}
          />
        <button onClick={uploadFile}>Upload File</button>
      </div>

      <div>
        <h2>Transações</h2>
        <ul>
          {transactions.map((report, key) => (
            <li>
              <table>
                <thead>
                  <tr>
                    <th >Cartão</th>
                    <th >CPF</th>
                    <th >Data</th>
                    <th >Dono da Loja</th>
                    <th >Hora</th>
                    <th >Nome da Loja</th>
                    <th >Tipo</th>
                    <th >Valor</th>
                  </tr>
                </thead>
                <tbody>
                  {report.transactions.map((transaction, key) => (
                    <tr>
                      <td >{transaction.opCard}</td>
                      <td >{transaction.opCpf}</td>
                      <td >{transaction.opDate}</td>
                      <td >{transaction.opStoreOwner}</td>
                      <td >{transaction.opHour}</td>
                      <td >{transaction.opStoreOwner}</td>
                      <td >{transaction.opType}</td>
                      <td >{transaction.opValue}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </li>          
          ))}
        </ul>
      </div>
    </div>
  );

  
}

export default App;
