
# Replace YOUR_API_TOKEN with your actual IBM Quantum API token
from qiskit_ibm_runtime import QiskitRuntimeService
 
# Save an IBM Quantum account and set it as your default account.
QiskitRuntimeService.save_account(channel="ibm_quantum", token="IBM_TOKEN", set_as_default=True)
 
# Load saved credentials
service = QiskitRuntimeService()