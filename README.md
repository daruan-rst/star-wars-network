<h2>Star Wars Resistence Social Network</h2>
<h3>Descrição do problema:</h3>
<i>O império continua sua luta incessante de dominar a galáxia, tentando ao máximo expandir seu território e eliminar os rebeldes. Você, como um soldado da resistência, foi designado para desenvolver um sistema para compartilhar recursos entre o rebeldes.</i>

<h3>Requisitos:</h3>
Você irá desenvolver uma API REST (sim, nós levamos a arquitetura da aplicação a sério mesmo no meio de uma guerra), ao qual irá armazenar informação sobre os rebeldes, bem como os recursos que eles possuem.

<h3>Adicionar rebeldes:</h3>
<ul>
<li>Um rebelde deve ter um nome, idade, gênero, localização(latitude, longitude e nome, na galáxia, da base ao qual faz parte).</li>
<li>Um rebelde também possui um inventário que deverá ser passado na requisição com os recursos em sua posse.</li>
<li>Atualizar localização do rebelde</li>
<li>Um rebelde deve possuir a capacidade de reportar sua última localização, armazenando a nova latitude/longitude/nome (não é necessário rastrear as localizações, apenas sobrescrever a última é o suficiente).</li>
</ul>

<h3>Reportar o rebelde como um traidor:</h3>
<ul>
<li>Eventualmente algum rebelde irá trair a resistência e se aliar ao império. Quando isso acontecer, nós precisamos informar que o rebelde é um traidor.</li>
<li>Um traidor não pode negociar os recursos com os demais rebeldes, não pode manipular seu inventário, nem ser exibido em relatórios.</li>
<li>Um rebelde é marcado como traidor quando, ao menos, três outros rebeldes reportarem a traição.</li>
<li>Uma vez marcado como traidor, os itens do inventário se tornam inacessíveis (eles não podem ser negociados com os demais).</li>
  </ul>
  
<h3>Rebeldes não podem Adicionar/Remover itens do seu inventário.</h3>
  <ul>
<li>Seus pertences devem ser declarados quando eles são registrados no sistema. Após isso eles só poderão mudar seu inventário através de negociação com os outros rebeldes.</li>
  </ul>
  
  
<h3>Negociar itens:</h3>
<ul>
<li>Os rebeldes poderão negociar itens entre eles.</li>
<li>Para isso, eles devem respeitar a tabela de preços abaixo, onde o valor do item é descrito em termo de pontos.</li>
<li>Ambos os lados deverão oferecer a mesma quantidade de pontos. Por exemplo, 1 arma e 1 água (1 x 4 + 1 x 2) valem 6 comidas (6 x 1) ou 2 munições (2 x 3).</li>
<li>A negociação em si não será armazenada, mas os itens deverão ser transferidos de um rebelde a outro.</li>
  </ul>

<table>
  <thead>
    <th>Item</th>
    <th>Pontos</th>
    <tr>
      <td>1 Arma</td>
      <td>4 pontos</td>
    </tr>
    <tr>
       <td>1 Munição</td>
       <td>3 pontos</td>
    </tr>
    <tr>
      <td>1 Água	</td>
      <td>2 pontos</td>
    </tr>
    <tr>
      <td>1 Comida</td>
      <td>1 ponto</td>
    </tr>
    </table>
      <h3>Relatórios:</h3>
      
 API deve oferecer os seguintes relatórios:
<ol>
  <li>Porcentagem de traidores.</li>
<li>Porcentagem de rebeldes.</li>
<li>Quantidade média de cada tipo de recurso por rebelde (Ex: 2 armas por rebelde).</li>
<li>Pontos perdidos devido a traidores.</li>
      </ol>      
      
  <h3>Notas:</h3>
  <ol>
 <li>Deverá ser utilizado Java, Spring boot e como gerenciador de dependência Maven.</li>
 <li>Não será necessário autenticação.</li>
 <li>Nós ainda nos preocupamos com uma programação adequada (código limpo) e técnicas de arquitetura, você deve demonstrar que é um digno soldado da resistência através das suas habilidades.</li>
 <li>Não esqueça de minimamente documentar os endpoints da sua API e como usa-los.</li>
 <li>Sua API deve estar minimamente coberta por testes (Unitários e/ou integração).</li>
 <li>Da descrição acima você pode escrever uma solução básica ou adicionar requisitos não descritos. Use seu tempo com sabedoria; Uma solução ótima e definitiva pode levar muito tempo para ser efetiva na guerra, então você deve trazer a melhor solução possível, que leve o mínimo de tempo, mas que ainda seja capaz de demonstrar suas habilidades e provar que você é um soldado valioso para a resistência.</li>
 <li>Comente qualquer dúvida e cada decisão tomada.</li>
        </ol>
