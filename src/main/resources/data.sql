INSERT INTO BOOKS (
                  id,
                  isbn,
                  num_pages,
                  price,
                  publish_date,
                  summary,
                  synopsis,
                  title
                  )
VALUES (
        'uuid-teste-12345',
        '123-123-teste-isbn',
        220,
        75.25,
        '2023-12-31',
        'Este resumo, lorem ipsum dolor sit amet, consectetur adipiscing ' ||
        'elit,sed do eiusmod tempor incididunt ut labore et dolore magna '' ||
        ''aliqua.',
        'Esta Sinopse ipsum dolor sit amet, consectetur adipiscing elit, ' ||
        'sed do eiusmod tempor incididunt ut labore et dolore magna ' ||
        'aliqua. Ut enim ad minim veniam, quis nostrud exercitation ' ||
        'ullamco laboris nisi ut aliquip ex ea commodo consequat. ' ||
        'Duis aute irure dolor in reprehenderit in voluptate velit ' ||
        'esse cillum dolore eu fugiat nulla pariatur. Excepteur sint ' ||
        'occaecat cupidatat non proident, sunt in culpa qui officia ' ||
        'deserunt mollit anim id est laborum.',
        'Titulo de Teste'
       );