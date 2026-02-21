package com.nubasu.spotify.webapi.wrapper.utils

@OptIn(ExperimentalUnsignedTypes::class)
internal object Sha256 {
    private val k = uintArrayOf(
        0x428a2f98u, 0x71374491u, 0xb5c0fbcfu, 0xe9b5dba5u,
        0x3956c25bu, 0x59f111f1u, 0x923f82a4u, 0xab1c5ed5u,
        0xd807aa98u, 0x12835b01u, 0x243185beu, 0x550c7dc3u,
        0x72be5d74u, 0x80deb1feu, 0x9bdc06a7u, 0xc19bf174u,
        0xe49b69c1u, 0xefbe4786u, 0x0fc19dc6u, 0x240ca1ccu,
        0x2de92c6fu, 0x4a7484aau, 0x5cb0a9dcu, 0x76f988dau,
        0x983e5152u, 0xa831c66du, 0xb00327c8u, 0xbf597fc7u,
        0xc6e00bf3u, 0xd5a79147u, 0x06ca6351u, 0x14292967u,
        0x27b70a85u, 0x2e1b2138u, 0x4d2c6dfcu, 0x53380d13u,
        0x650a7354u, 0x766a0abbu, 0x81c2c92eu, 0x92722c85u,
        0xa2bfe8a1u, 0xa81a664bu, 0xc24b8b70u, 0xc76c51a3u,
        0xd192e819u, 0xd6990624u, 0xf40e3585u, 0x106aa070u,
        0x19a4c116u, 0x1e376c08u, 0x2748774cu, 0x34b0bcb5u,
        0x391c0cb3u, 0x4ed8aa4au, 0x5b9cca4fu, 0x682e6ff3u,
        0x748f82eeu, 0x78a5636fu, 0x84c87814u, 0x8cc70208u,
        0x90befffau, 0xa4506cebu, 0xbef9a3f7u, 0xc67178f2u,
    )

    fun digest(input: ByteArray): ByteArray {
        var h0 = 0x6a09e667u
        var h1 = 0xbb67ae85u
        var h2 = 0x3c6ef372u
        var h3 = 0xa54ff53au
        var h4 = 0x510e527fu
        var h5 = 0x9b05688cu
        var h6 = 0x1f83d9abu
        var h7 = 0x5be0cd19u

        val padded = pad(input)
        val w = UIntArray(64)
        var offset = 0
        while (offset < padded.size) {
            for (i in 0 until 16) {
                val j = offset + i * 4
                w[i] =
                    ((padded[j].toUInt() and 0xffu) shl 24) or
                    ((padded[j + 1].toUInt() and 0xffu) shl 16) or
                    ((padded[j + 2].toUInt() and 0xffu) shl 8) or
                    (padded[j + 3].toUInt() and 0xffu)
            }
            for (i in 16 until 64) {
                val s0 = w[i - 15].rotateRight(7) xor w[i - 15].rotateRight(18) xor (w[i - 15] shr 3)
                val s1 = w[i - 2].rotateRight(17) xor w[i - 2].rotateRight(19) xor (w[i - 2] shr 10)
                w[i] = w[i - 16] + s0 + w[i - 7] + s1
            }

            var a = h0
            var b = h1
            var c = h2
            var d = h3
            var e = h4
            var f = h5
            var g = h6
            var h = h7

            for (i in 0 until 64) {
                val s1 = e.rotateRight(6) xor e.rotateRight(11) xor e.rotateRight(25)
                val ch = (e and f) xor (e.inv() and g)
                val temp1 = h + s1 + ch + k[i] + w[i]
                val s0 = a.rotateRight(2) xor a.rotateRight(13) xor a.rotateRight(22)
                val maj = (a and b) xor (a and c) xor (b and c)
                val temp2 = s0 + maj

                h = g
                g = f
                f = e
                e = d + temp1
                d = c
                c = b
                b = a
                a = temp1 + temp2
            }

            h0 += a
            h1 += b
            h2 += c
            h3 += d
            h4 += e
            h5 += f
            h6 += g
            h7 += h

            offset += 64
        }

        val out = ByteArray(32)
        val hash = uintArrayOf(h0, h1, h2, h3, h4, h5, h6, h7)
        for (i in hash.indices) {
            val v = hash[i]
            val j = i * 4
            out[j] = ((v shr 24) and 0xffu).toByte()
            out[j + 1] = ((v shr 16) and 0xffu).toByte()
            out[j + 2] = ((v shr 8) and 0xffu).toByte()
            out[j + 3] = (v and 0xffu).toByte()
        }
        return out
    }

    private fun pad(input: ByteArray): ByteArray {
        val bitLen = input.size.toULong() * 8uL
        val rem = (input.size + 1) % 64
        val zeroPad = if (rem <= 56) 56 - rem else 120 - rem
        val out = ByteArray(input.size + 1 + zeroPad + 8)
        input.copyInto(out, 0, 0, input.size)
        out[input.size] = 0x80.toByte()
        for (i in 0 until 8) {
            out[out.size - 1 - i] = ((bitLen shr (8 * i)) and 0xffuL).toByte()
        }
        return out
    }
}
