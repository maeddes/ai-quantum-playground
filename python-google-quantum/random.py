import cirq

# Specify the number of bits you want (e.g., 4)
num_bits = 4

# Create qubits
qubits = [cirq.LineQubit(i) for i in range(num_bits)]

circuit = cirq.Circuit()

# Apply Hadamard gate to each qubit
for qubit in qubits:
  circuit.append(cirq.H(qubit))

# Measure all qubits and store results with unique keys
for i, qubit in enumerate(qubits):
  circuit.append(cirq.measure(qubit, key=f'bit_{i}'))

# Run the simulation
simulator = cirq.Simulator()
results = simulator.run(circuit, repetitions=1)

# Extract the random bit string
random_bits = ''.join([str(results.measurements[f'bit_{i}'][0]) for i in range(num_bits)])

print("Random bit string:", random_bits)