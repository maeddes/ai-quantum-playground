from qiskit.circuit.library import RealAmplitudes
from qiskit.circuit import QuantumCircuit, QuantumRegister, ClassicalRegister
from qiskit.quantum_info import SparsePauliOp
from qiskit.transpiler.preset_passmanagers import generate_preset_pass_manager
from qiskit_ibm_runtime import SamplerV2 as Sampler
from qiskit_ibm_runtime.fake_provider import FakeManilaV2
 
backend = FakeManilaV2()

# Create a Quantum Circuit with one qubit
qr = QuantumCircuit(1)

# Apply the Hadamard gate
qr.h(0)

# Number of shots for measurement (more shots improve randomness)
shots = 1024

# Run the circuit on the backend
job = backend.run(qr, shots=shots)

# Get the results
results = job.result()

# Extract the bitstring (each shot is a 0 or 1)
counts = results.get_counts(qr)
bitstring = next(iter(counts.keys()))  # Get the first bitstring

# Choose a random bit or convert the bitstring to an integer
random_bit = bitstring[0]  # Get the first bit (0 or 1)
# random_int = int(bitstring, 2)  # Convert to integer (for a range of numbers)

print("Random bit (0 or 1):", random_bit)