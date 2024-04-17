
# Assinador e verificador

Um desafio proposto pelo chefe da CGICP, criar uma API que assine arquivos e uma que valide assinaturas, de pouco em pouco vamos implementando novas funções.




## Documentação

Esse projeto pode ser executado em duas etapas. O primeiro arquivo "SignatureAPI" pode ser utilizado para assinar arquivos, já o segundo arquivo "VerificationAPI" pode ser utilizado para verificar as assinaturas.

Ambos foram feitos de maneira independente e rodam de maneira independente.

### Para assinar:
Uma vez que o arquivo for assinado, a API tranforma o arquvio em **Base64**, em seguida extrai o **hash** do arquivo, ademais, este hash é assina utilizadno a lógica da **Criptografia RSA** com um tamanho de **2048 bits** e o modelo **SHA256**, e esse hash assinado é salvo como um arquivo **".sig"** e a chave pública utilizada para fazer o processo da assinatura é salva na pasta raiz, em um arquivo chamado **"publicKey.txt"**.

### Para verificar a assinatura:
Tendo em mãos o arquivo assinado **".sig"** e o arquivo **original** a verificação pode ser feita. 

Primeiro rode o arquivo **"VerificationAPI"**, assim que este arquivo for executado, uma **interface gráfica** será aberta solicitando que você **selecione** o arquivo **original**, e assim que este for selecionado, outro seletor será aberto, neste momento você deve **selecionar** o **arquivo assinado ".sig"**.

A API então fará a **verificação** do **hash** do **arquivo original**, e irá **comparar** ela com a do **arquivo assinado ".sig"**. Se os hashs forem **iguais**, a **assinatura** será **validada**, caso **contrario**, ela será **reprovada**. 

***Lembre-se que uma vez assinado, o arquivo não pode ser modificado. Caso ele seja modificado, a assinatura dará como inválida.***

