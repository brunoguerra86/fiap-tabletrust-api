#
# Funcionalidade Feedback
#
# Cenario: Registrar um novo feedback
  Feature: FeedBack

    Scenario: Registrar Feedback
      Given registrar um novo feedback
      Then o feedback foi registrado com sucesso

    Scenario: Listar os feedbacks
      Given Varios feedbacks foram registrados
      When Uma busca por feedbacks é realizada
      Then Eles sao exibidos com sucesso