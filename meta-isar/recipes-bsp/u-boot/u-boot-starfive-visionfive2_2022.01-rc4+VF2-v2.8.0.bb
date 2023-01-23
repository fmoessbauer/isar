#
# Copyright (c) Siemens AG, 2023
#
# SPDX-License-Identifier: MIT

require recipes-bsp/u-boot/u-boot-custom.inc

SRC_URI += " \
    git://github.com/starfive-tech/u-boot.git;branch=JH7110_VisionFive2_devel;protocol=https;destsuffix=u-boot-${PV} \
    file://starfive-visionfive2/0001-riscv-Fix-build-against-binutils-2.38.patch \
    file://starfive-visionfive2/0002-riscv-support-building-double-float-modules.patch \
    file://starfive-visionfive2/0001-fix-offset-of-env-data-block-on-jh7110.patch \
    file://starfive-visionfive2/0002-exclude-opensbi-memory-range-in-device-tree.patch \
    "
SRCREV = "f1d959f0b02e16842181a4c1723ba3ea30d2e04a"

DEBIAN_BUILD_DEPENDS .= ", libssl-dev:${HOST_ARCH}, libssl-dev:${DISTRO_ARCH}"

# TODO: analyze the strange cross gcc include chain
DEBIAN_BUILD_DEPENDS .= ", libc6-dev-i386:${HOST_ARCH}"

U_BOOT_CONFIG = "starfive_visionfive2_defconfig"
U_BOOT_BIN = "u-boot.bin"
U_BOOT_SPL_BIN = "spl/u-boot-spl.bin"

S = "${WORKDIR}/u-boot-${PV}"

# install dtb files for opensbi
do_prepare_build[cleandirs] += "${S}/debian"
do_prepare_build:append() {
    # also build and install spl component
    sed -i 's|${U_BOOT_BIN}|${U_BOOT_BIN} ${U_BOOT_SPL_BIN}|g' ${S}/debian/rules
    echo "${U_BOOT_SPL_BIN} usr/lib/u-boot/${MACHINE}/" \
        >> ${S}/debian/u-boot-${MACHINE}.install
    # install device tree
    echo "arch/riscv/dts/*.dtb usr/share/u-boot/${MACHINE}/" \
        >> ${S}/debian/u-boot-${MACHINE}-dev.install
}
