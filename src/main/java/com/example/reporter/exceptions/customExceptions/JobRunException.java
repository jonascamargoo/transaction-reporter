package com.example.reporter.exceptions.customExceptions;

public class JobRunException extends RuntimeException {
    private static final String DEFAULT_MSG1 = "Já existe uma execução em andamento para o trabalho. Aguarde a conclusão da execução atual antes de iniciar uma nova.";
    private static final String DEFAULT_MSG2 = "Erro ao tentar reiniciar o trabalho. Certifique-se de que as condições necessárias para reiniciar estão atendidas e verifique se não há problemas internos impedindo a reinicialização.";
    private static final String DEFAULT_MSG3 = "Não é possível reiniciar o trabalho, pois a instância do trabalho já foi concluída anteriormente com êxito.";
    private static final String DEFAULT_MSG4 = "Os parâmetros fornecidos para a execução do trabalho são inválidos. Certifique-se de que os parâmetros necessários estejam presentes e tenham valores válidos.";

    public JobRunException(String message) {
        super(message);
    }

    public static JobRunException alreadyRunningException() {
        return new JobRunException(DEFAULT_MSG1);
    }

    public static JobRunException restartException() {
        return new JobRunException(DEFAULT_MSG2);
    }

    public static JobRunException alreadyCompleteException() {
        return new JobRunException(DEFAULT_MSG3);
    }

    public static JobRunException invalidParametersException() {
        return new JobRunException(DEFAULT_MSG4);
    }


}
