// Tu función togglePassword parece estar bien. Solo asegúrate de que el selector '.toggle-password i' sea correcto.
function togglePassword() {
    var passwordInput = document.getElementById('password');
    var passwordButton = document.querySelector('.toggle-password i');
    if (passwordInput.type === 'password') {
      passwordInput.type = 'text';
      passwordButton.classList.remove('fa-eye');
      passwordButton.classList.add('fa-eye-slash');
    } else {
      passwordInput.type = 'password';
      passwordButton.classList.remove('fa-eye-slash');
      passwordButton.classList.add('fa-eye');
    }
  }
  
  // Este es el código para añadir el evento después de que el DOM esté cargado.
  document.addEventListener('DOMContentLoaded', function() {
    var togglePasswordButton = document.querySelector('.toggle-password');
    togglePasswordButton.addEventListener('click', togglePassword);
  });
  